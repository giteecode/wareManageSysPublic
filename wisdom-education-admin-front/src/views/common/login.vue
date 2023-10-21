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
          </el-form-item>

          <el-form-item  prop="code">
            <el-row :gutter="20">
              <el-col :span="14">
                <el-input v-model="form.code" placeholder="验证码">
                </el-input>
              </el-col>
              <el-col :span="10" class="login-captcha">
                <img :src="imgUrl" @click="changeCode()" title="换一张" alt="换一张">
              </el-col>
            </el-row>
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
// eslint-disable-next-line no-unused-vars
  import {encrypt} from '@/utils/aes'
  export default {
    data () {
      return {
        key: null,
        form: {
          userName: '',
          password: '',
          code: '',
          checked: false
        },
        imgUrl: '',
        rules: {
          userName: [
            { required: true, message: '帐号不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],

          code: [
            { required: true, message: '请输入验证码', trigger: 'blur' }
          ]
        }
      }
    },

    mounted () {
      this.changeCode()
    },

    methods: {

      changeCode () {
        this.key = new Date().getTime()
        this.imgUrl = this.$http.httpUrl('/api/image?key=' + this.key)
      },

      // 提交表单
      doLogin () {
        let that = this
        that.$refs['form'].validate(function (valid, rules) {
          if (valid) {
            that.form.key = that.key
            that.axios.post(that.$http.httpUrl('/system/login'), that.form)
              .then(function (response) {
                if (response.data.code === 1) {
                  that.$notify({
                    message: response.data.message,
                    type: 'success'
                  })

                  localStorage.setItem('Authorization', response.headers.authorization)
                  localStorage.setItem('userInfo', JSON.stringify(response.data.data.userInfo))
                  setTimeout(function () {
                    that.$router.push('home')
                  }, 2000)
                } else if (response.data.code === 2) {
                  that.$notify({
                    message: response.data.message,
                    type: 'error'
                  })
                  that.changeCode()
                }
              })
          } else {
            let message = ''
            for (let rule in rules) {
              message = rules[rule][0].message
              if (message) {
                break
              }
            }
            that.$message({
              type: 'error',
              message: message
            })
            return false
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
    top: 12%;
    color: #409eff;
    font-weight: 700;
    font-size: 24px;
    margin-bottom: 20px;
    text-align: center;
  }

  .box-card {
    height: 410px;
    width: 420px;
    position: relative;
    top: 20%;
    left: 36%;
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
    background: url("/static/img/login-bg.jpg");
    background-size:100% 100%; //   background: url('/static/image/loginng.gif') no-repeat;
    background-position: center;
    position: fixed;
  }
</style>
