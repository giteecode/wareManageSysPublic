<template>
  <div class="loginpage">
    <el-card class="box-card">
      <div class="title">智慧云智能教育平台</div>

      <div class="login-form">
        <el-form ref="form" :model="form" :rules="rules" @keyup.enter.native="doLogin()" label-width="80px">
          <el-form-item prop="userName">
            <el-input v-model="form.userName" placeholder="用户名" prefix-icon="el-icon-user-solid"></el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="el-icon-key"></el-input>
            <el-checkbox v-model="form.checked" style="margin-top: 7px">一周内自动登录</el-checkbox>
            <span class="forget">忘记密码?</span>
          </el-form-item>

          <el-form-item>
            <el-button @click="doLogin" type="primary">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>

  import {encrypt} from '@/api/Aes'
  export default {
    name: 'login',
    data () {
      return {
        rules: {
          userName: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
          ],
        },
        imgUrl: this.$httpApi.httpUrl('/api/image'),
        form: {
          userName: '',
          password: '',
          checked: false
        }
      }
    },

    mounted() {
      let params = {
        id: 1,
        name: 'java',
        address: 'test'
      }
      console.log(JSON.stringify(params))
      console.log(encrypt(JSON.stringify(params)))
    },

    methods: {
      doLogin () {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.axios.post(this.$httpApi.httpUrl('/student/login'), this.form)
              .then(response => {
                let result = response.data.data
                let token = response.headers.authorization
                this.$store.commit('user/updateToken', token)
                let userInfo = {
                  name: result.name,
                  id: result.id,
                  headImg: result.headImg,
                  sex: result.sex,
                  age: result.age,
                  gradeInfoName: result.gradeInfoName,
                  gradeInfoId: result.gradeInfoId,
                  address: result.address,
                  mobile: result.mobile,
                }
                this.$store.commit('user/updateUserInfo', userInfo)
                this.$message({
                  type: 'success',
                  message: response.data.message
                })
                setTimeout(() =>{
                  this.$router.push('/home')
                }, 1000)
              })
          }
        })
      }
    }
  }
</script>

<style lang="less" scoped>

  .forget {
    float: right;
    margin-top: 7px;
    cursor: pointer;
  }
  .forget:hover {
    color: #409eff;
  }
  .title {
    position: absolute;
    left: 25%;
    top: 10%;
    color: #409eff;
    font-weight: 700;
    font-size: 24px;
    margin-bottom: 20px;
    text-align: center;
  }

  .box-card {
    height: 380px;
    width: 420px;
    position: relative;
    top: 20%;
    left: 60%;
  }

  .el-button {
    width: 100%;
  }
  .login-form {
    width: 420px;
    position: absolute;
    top: 30%;
    right: 10%;
  }
  .loginpage{
    height: 100%;
    width: 100%;
    box-sizing: border-box;
    background: url("/static/image/timg.jpg");
    background-size:100% 100%; //   background: url('/static/image/loginng.gif') no-repeat;
    background-position: center;
    position: fixed;
  }
</style>
