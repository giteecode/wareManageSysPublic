<template>
  <el-dialog
    title="修改密码"
    :visible.sync="visible"
    :append-to-body="true">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="账号">
        <span>{{ userName }}</span>
      </el-form-item>
      <el-form-item label="原密码" prop="password">
        <el-input type="password" v-model="dataForm.password"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input type="password" v-model="dataForm.newPassword"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input type="password" v-model="dataForm.confirmPassword"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  // import { clearLoginInfo } from '@/utils'
  export default {
    data () {
      let validateConfirmPassword = (rule, value, callback) => {
        if (this.dataForm.newPassword !== value) {
          callback(new Error('确认密码与新密码不一致'))
        } else {
          callback()
        }
      }
      return {
        visible: false,
        dataForm: {
          password: '',
          newPassword: '',
          confirmPassword: ''
        },
        dataRule: {
          password: [
            { required: true, message: '原密码不能为空', trigger: 'blur' }
          ],
          newPassword: [
            { required: true, message: '新密码不能为空', trigger: 'blur' }
          ],
          confirmPassword: [
            { required: true, message: '确认密码不能为空', trigger: 'blur' },
            { validator: validateConfirmPassword, trigger: 'blur' }
          ]
        }
      }
    },
    computed: {
      userName: {
        get () { return this.$store.state.user.name }
      },
      mainTabs: {
        get () { return this.$store.state.common.mainTabs },
        set (val) { this.$store.commit('common/updateMainTabs', val) }
      }
    },

    mounted () {
      let userInfo = JSON.parse(sessionStorage.getItem('userInfo'))
      this.userName = userInfo.login_name
   //   this.dataForm.id = userInfo.id
    },

    methods: {
      // 表单提交
      dataFormSubmit () {
        let that = this
        that.$refs['form'].validate(function (valid, rules) {
          if (valid) {
            that.axios.post('/system/admin/password', that.dataForm)
              .then(function (response) {
                that.visible = false
                that.dataForm = {
                  password: '',
                  newPassword: '',
                  confirmPassword: ''
                }
                if (response.data.code === 1) {
                  that.$message({
                    type: 'success',
                    message: response.data.message
                  })
                } else {
                  that.$message({
                    type: 'error',
                    message: response.data.message
                  })
                }
              }).catch(function (error) {
                console.log(error)
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

