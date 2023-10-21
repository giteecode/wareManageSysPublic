<template>
  <nav class="site-navbar" :class="'site-navbar--' + navbarLayoutType">
    <div class="site-navbar__header">
      <h5 class="site-navbar__brand" @click="$router.push({ name: 'home' })">
        <a class="site-navbar__brand-lg" href="javascript:;">智慧云智能教育平台</a>
      </h5>
    </div>
    <div class="site-navbar__body clearfix">
      <el-menu
        class="site-navbar__menu"
        mode="horizontal">
        <el-menu-item class="site-navbar__switch" index="0" @click="sidebarFold = !sidebarFold">
          <icon-svg name="zhedie"></icon-svg>
        </el-menu-item>
      </el-menu>
      <el-menu
        class="site-navbar__menu site-navbar__menu--right"
        mode="horizontal">
        <el-menu-item index="1" @click="$router.push({ name: 'theme' })">
          <template slot="title">
            <el-badge style="font-size: 25px;" value="new">
              <icon-svg name="indexSetting" ></icon-svg>
            </el-badge>
          </template>
        </el-menu-item>

        <el-menu-item index="2" @click="readMessage">
          <template v-if="hasMessage" slot="title">
            <el-badge style="font-size: 25px">
              <icon-svg name="message"></icon-svg>
            </el-badge>
          </template>

          <template v-else="hasMessage" slot="title">
            <el-badge style="font-size: 25px" :value="messageCount">
              <icon-svg name="message"></icon-svg>
            </el-badge>
          </template>
        </el-menu-item>

        <el-menu-item class="site-navbar__avatar" index="3">
          <el-dropdown :show-timeout="0" placement="bottom">
            <span class="el-dropdown-link">
              <img src="~@/assets/img/avatar.png" :alt="userName">{{ userName }}
            </span>
            <el-dropdown-menu slot="dropdown">
              <!-- <el-dropdown-item>个人资料</el-dropdown-item>-->
              <el-dropdown-item @click.native="beforeUpdatePassword">修改密码</el-dropdown-item>
              <el-dropdown-item @click.native="logout()">退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-menu-item>
      </el-menu>
    </div>

    <el-dialog
      title="修改密码"
      :visible.sync="showFlag"
      append-to-body>
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="updatePassword()" label-width="80px">
        <el-form-item label="账号">
          <el-input :disabled="true" v-model="userName"></el-input>
        </el-form-item>
        <el-form-item label="原密码" prop="password">
          <el-input :type="passwordType" @focus="changeInputType" v-model="dataForm.password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input :type="passwordType" @focus="changeInputType" v-model="dataForm.newPassword"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input :type="passwordType" @focus="changeInputType" v-model="dataForm.confirmPassword"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="showFlag = false">取消</el-button>
      <el-button type="primary" @click="updatePassword">确定</el-button>
    </span>
    </el-dialog>
  </nav>
</template>

<script>
import { clearLoginInfo } from '@/utils'
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
      lockConnection: false,
      showFlag: false,
      hasMessage: false,
      dataForm: {
        password: '',
        newPassword: '',
        confirmPassword: ''
      },
      heartStartTimer: null, // 心跳定时器
      socketCloseTimer: null,
      webSocketTimeOutSecond: 10,
      webSocketTimeOutUnit: 1000, // 默认10秒
      userInfo: {},
      webSocketTimer: null,
      messageCount: '',
      passwordType: 'text',
      webSocket: null,
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

  destroyed () {
    clearInterval(this.webSocketTimer)
    this.webSocket.close()
  },

  mounted () {
    this.initWebSocket()
    this.userInfo = JSON.parse(localStorage.getItem('userInfo'))
  },

  computed: {
    navbarLayoutType: {
      get () { return this.$store.state.common.navbarLayoutType }
    },
    sidebarFold: {
      get () { return this.$store.state.common.sidebarFold },
      set (val) { this.$store.commit('common/updateSidebarFold', val) }
    },
    mainTabs: {
      get () { return this.$store.state.common.mainTabs },
      set (val) { this.$store.commit('common/updateMainTabs', val) }
    },
    userName: {
      get () { return this.$store.state.user.name }
    }
  },
  methods: {

    initWebSocket () {
      if ('WebSocket' in window) {
        let isHttps = document.location.protocol === 'https:'
        let hostName = this.$http.getWebSocketHost()
        if (isHttps) { // https 需要使用 wss:// 方式 连接
          this.webSocket = new WebSocket('wss://' + hostName + '/webSocket')
        } else {
          this.webSocket = new WebSocket('ws://' + hostName + '/webSocket')
        }
        this.webSocket.onopen = this.onOpen
        this.webSocket.onmessage = this.onMessage
        this.webSocket.onclose = this.onClose
        this.webSocket.onerror = this.onError
      }
    },

    readMessage () {
      this.$router.push({
        name: 'message',
        readFlag: 1
      })
    },

    changeInputType () {
      this.passwordType = 'password'
    },

    onOpen () {
      let message = {
        userId: this.userInfo.id,
        token: localStorage.getItem('Authorization'),
        messageType: 10010,
        platform: 'educationAdmin'
      }
      this.heartStart() // 开启心跳机制
      this.webSocket.send(JSON.stringify(message))
      if (this.webSocketTimer != null) {
        clearInterval(this.webSocketTimer) // 清空重连定时器
      }
      // console.log('webSocket连接成功')
    },

    getMessageList () {
      let that = this
      that.axios.get(this.$http.httpUrl('/system/message'), {
        params: {
          readFlag: 0
        }
      }).then((response) => {
        let totalCount = response.data.data.total
        if (totalCount > 0) {
          that.hasMessage = true
          that.messageCount = totalCount
        }
      })
    },

    heartStart () {
      let msg = {
        messageType: 10001,
        platformType: 'educationAdmin',
        msgContent: 'ok'
      }
      this.webSocket.send(JSON.stringify(msg))
      this.socketCloseTimer = setTimeout(() => { // 10秒未收到服务器响应关闭连接
        this.webSocket.close()
      }, 3000)
    },

    onError () {
      console.log('websocket 连接error')
      if (this.lockConnection) {
        return
      }
      this.tryConnection()
    },

    // 接收消息
    onMessage (event) { // 数据接收
      let data = JSON.parse(event.data)
      // 账号被踢除下线
      if (data.messageType === 10012) {
        this.$notify({
          title: '警告',
          message: data.msgContent,
          type: 'warning'
        })
        setTimeout(() => {
          clearLoginInfo()
          this.$router.push('login')
        }, 5000)
      }

      // 心跳包
      if (data.messageType === 10001) {
        clearTimeout(this.socketCloseTimer) // 清除关闭socket连接定时器
        setTimeout(() => {
          this.heartStart() // 重置心跳
        }, 5000)
      }
    },

    onClose (event) {  // 关闭
      console.log('websocket 连接 close')
      if (this.lockConnection) {
        return
      }
      this.tryConnection()
    },

    /**
     * 尝试重新连接
     */
    tryConnection () {
      if (this.lockConnection) {
        return // 重连锁，防止出现死循环
      }

      this.lockConnection = true
      clearTimeout(this.webSocketTimer)
      this.webSocketTimer = setTimeout(() => {
       // console.log('tryConnection')
        this.initWebSocket()
        this.lockConnection = false
        // 延迟下一次重连的时间 , 避免请求次数过多造成性能问题
        // this.webSocketTimeOutSecond = this.webSocketTimeOutSecond + parseInt(this.webSocketTimeOutSecond, 2)
      }, 10000)
    },

    // 修改密码
    beforeUpdatePassword () {
      this.showFlag = true
    },

    updatePassword () {
      let that = this
      that.$refs['dataForm'].validate(function (valid, rules) {
        if (valid) {
          that.axios.post(that.$http.httpUrl('/system/admin/resettingPassword'), that.dataForm)
            .then(function (response) {
              that.dataForm = {
                password: '',
                newPassword: '',
                confirmPassword: ''
              }
              if (response.data.code === 1) {
                that.showFlag = false
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
    },

    logoutCommon (that) {
      that.axios.post(that.$http.httpUrl('/system/logout')).then(function (response) {
        if (response.data.code === 1) {
          that.$message({
            type: 'success',
            message: response.data.message
          })
          clearLoginInfo()
          setTimeout(function () {
            that.$router.push({name: 'login'})
          }, 1000)
        } else {
          that.$message({
            type: 'error',
            message: response.data.message
          })
        }
      })
    },

    // 退出
    logout () {
      let that = this
      that.$confirm('确定退出系统吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        that.logoutCommon(that)
      }).catch(() => {
        that.$message({
          type: 'info',
          message: '已取消'
        })
      })
    }
  }
}
</script>
<style scoped>
.site-navbar__brand {
  font-size: 16px !important;
}
.site-navbar {
  background: #0e90d2;
}
</style>
