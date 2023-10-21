<template>
    <div class="user_content">
      <el-form
        :model="passwordForm"
        :rules="rules"
        ref="passwordForm"
        label-width="100px" class="demo-ruleForm">

        <el-form-item label="原密码" prop="password">
          <el-input type="password" v-model="passwordForm.password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input type="password" v-model="passwordForm.newPassword"></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input type="password" v-model="passwordForm.confirmPassword"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button @click="updatePassword" type="primary">修改</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
    export default {
      name: 'passWord',
      data () {
        let validatorPassword = (rule, value, callback) => {
          if (!value) {
            callback(new Error('请输入确认密码'));
          } else {
            if (value !== this.passwordForm.newPassword) {
              callback(new Error('确认密码与新密码不一致'))
            }
          }
          callback();
        }
        return {
          passwordForm: {
            password: '',
            newPassword: '',
            confirmPassword: ''
          },

          rules: {
            password: [
              { required: true, message: '请输入密码', trigger: 'blur' }
            ],
            newPassword: [
              { required: true, message: '请输入新密码', trigger: 'change' }
            ],
            confirmPassword: [
              { required: true, message: '请输入确认密码', trigger: 'change' },
              { required: true, validator: validatorPassword, trigger: 'blur' }
            ]
          }
        }
      },

      beforeCreate () {
        sessionStorage.setItem('userMenuIndex', '1-2')
      },

      mounted() {

      },

      methods: {
        updatePassword () {
          this.$refs['passwordForm'].validate((valid) => {
            if (valid) {
              this.axios.post(this.$httpApi.httpUrl('/student/resetting/password'), this.passwordForm)
                .then(response => {
                   this.$message.success(response.data.message)
                   this.passwordForm = {
                      password: '',
                      newPassword: '',
                      confirmPassword: ''
                   }
                })
            }
          })
        }
      }
    }
</script>

<style scoped>
  .user_content {
    margin-left: 5%;
  }
</style>
