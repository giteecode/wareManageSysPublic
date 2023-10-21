<template>
  <div>
    <keep-alive>
      <div class="header">
        <div class="systemtitle">
          <img src="/static/image/logo.png" alt="" />
        </div>

        <div style="width: 800px; float: left">
          <el-menu
            style="margin-top: 20px"
            :default-active="menuIndex"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
          >
            <el-menu-item
              v-for="(menu, index) in menuList"
              :key="index"
              @click="$router.push({ path: menu.path })"
              :index="index + 1 + ''"
              >{{ menu.label }}</el-menu-item
            >
          </el-menu>
        </div>

        <el-dropdown style="right: 13%">
          <el-badge
            v-if="unReadMessageNumber > 0"
            :value="unReadMessageNumber"
            class="item"
          >
            <span style="font-size: 16px" class="el-icon-message">消息</span>
          </el-badge>

          <el-badge v-else class="item">
            <span class="el-icon-message">消息</span>
          </el-badge>

          <el-dropdown-menu>
            <el-dropdown-item @click.native="goMessageView()">
              <el-badge
                v-if="unReadMessageNumber > 0"
                :value="unReadMessageNumber"
                class="item"
              >
                <span>通知</span>
              </el-badge>

              <el-badge v-else class="item">
                <span>通知</span>
              </el-badge>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <el-dropdown>
          <span class="el-dropdown-link">
            <img v-if="!userInfo.headImg" src="@/assets/img/userImg.png" />
            <img v-else :src="userInfo.headImg" />
            {{ userInfo.name }}
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <!--<el-dropdown-item>个人资料</el-dropdown-item>-->
            <!--  <el-dropdown-item>
              <el-badge :value="unReadMessageNumber" class="item">
                <span>消息通知</span>
              </el-badge>
            </el-dropdown-item>-->
            <el-dropdown-item @click.native="goUserCenter()"
              >个人中心</el-dropdown-item
            >
            <el-dropdown-item @click.native="logout()">退出</el-dropdown-item>
          </el-dropdown-menu>

          <el-dialog
            append-to-body
            title="修改密码"
            :visible.sync="dialogFormVisible"
          >
            <el-form :model="form">
              <el-form-item label="用户名" :label-width="formLabelWidth">
                <el-input
                  v-model="userInfo.name"
                  :disabled="true"
                  autocomplete="off"
                ></el-input>
              </el-form-item>

              <el-form-item label="密码" :label-width="formLabelWidth">
                <el-input
                  v-model="form.newPassword"
                  autocomplete="off"
                  show-password
                ></el-input>
              </el-form-item>
              <el-form-item label="确认密码" :label-width="formLabelWidth">
                <el-input
                  v-model="form.confirmPassword"
                  autocomplete="off"
                  show-password
                ></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="dialogFormVisible = false"
                >确 定</el-button
              >
            </div>
          </el-dialog>
        </el-dropdown>
      </div>
    </keep-alive>
  </div>
</template>

<script>
import { clearLoginInfo } from "@/api/common";
import SocketMessageType from "@/enum/SocketMessageType";
import { mapGetters } from "vuex";
export default {
  name: "header-common",
  data() {
    return {
      // activeIndex: '1',
      lockConnection: false,
      socketCloseTimer: null,
      dialogTableVisible: false,
      dialogFormVisible: false,
      fileHost: this.$store.state.common.fileHost,
      form: {
        newPassword: "",
        confirmPassword: ""
      },
      unReadMessageNumber: 0,
      formLabelWidth: "120px",
      defaultSelectPage: null,
      menuList: [
        { label: "首页", id: 1, active: "", isT: false, path: "/home" },
        {
          label: "视频课堂",
          id: 2,
          active: "",
          isT: false,
          path: "/courseVideo"
        },
        { label: "考试中心", id: 3, active: "", isT: false, path: "/paper" },
        {
          label: "考试记录",
          id: 4,
          active: "",
          isT: false,
          path: "/examHistory"
        },
        {
          label: "我的错题本",
          id: 5,
          active: "",
          isT: false,
          path: "/wrongBook"
        }
      ],
      webSocket: null
    };
  },

  watch: {
    $route() {
      // 获取当前路径
      let path = this.$route.path;

      // 检索当前路径
      this.parserPath(path);
    }
  },

  computed: {
    ...mapGetters({
      userInfo: "user/getUserInfo",
      token: "user/getToken",
      webSiteConfig: "common/getWebSiteConfig",
      menuIndex: "common/getMenuIndex"
    })
  },

  destroyed() {
    clearInterval(this.webSocketTimer);
  },

  mounted() {
    this.initWebSocket();
    this.getUnReadMessageNumber();
  },

  methods: {
    handleSelect(key, keyPath) {},

    getUnReadMessageNumber() {
      this.axios
        .get(this.$httpApi.httpUrl("/student/message/unReadNum"))
        .then(response => {
          this.unReadMessageNumber = response.data.data;
        });
    },

    goMessageView() {
      this.unReadMessageNumber = 0;
      this.$router.push({ name: "message" });
    },

    parserPath(path) {
      let selected = false;
      this.menuList.forEach(menu => {
        if (menu.path.indexOf(path) !== -1) {
          this.$store.commit("common/updateMenuIndex", menu.id + "");
          selected = true;
          return;
        }
      });

      if (!selected) {
        this.$store.commit("common/updateMenuIndex", null);
      }
    },

    goUserCenter() {
      this.$router.push({ path: "/account/userInfo" });
    },

    logoutCommon(that) {
      that.axios
        .post(that.$httpApi.httpUrl("/student/logout"))
        .then(function(response) {
          if (response.data.code === 1) {
            that.$message({
              type: "success",
              message: response.data.message
            });
            clearLoginInfo();
            setTimeout(function() {
              that.$router.push({ name: "login" });
            }, 1000);
          } else {
            that.$message({
              type: "error",
              message: response.data.message
            });
          }
        });
    },

    initWebSocket() {
      if ("WebSocket" in window) {
        let isHttps = "https:" == document.location.protocol ? true : false;
        let hostName = this.$store.state.common.webSocketHost;
        if (isHttps) {
          // https 需要使用 wss:// 方式 连接
          this.webSocket = new WebSocket("wss://" + hostName + "/webSocket");
        } else {
          this.webSocket = new WebSocket("ws://" + hostName + "/webSocket");
        }
        this.webSocket.onopen = this.onOpen;
        this.webSocket.onerror = this.onError;
        this.webSocket.onmessage = this.onMessage;
        this.webSocket.onclose = this.onClose;
      }
    },

    onOpen() {
      let message = {
        token: this.token,
        userId: this.userInfo.id,
        messageType: SocketMessageType.CONNECTION_SUCCESS.code,
        platform: "educationStudent"
      };
      this.heartStart(); // 开启心跳机制
      this.webSocket.send(JSON.stringify(message));
      if (this.webSocketTimer != null) {
        clearInterval(this.webSocketTimer); // 清空重连定时器
      }
    },

    heartStart() {
      let msg = {
        messageType: SocketMessageType.HEART.code,
        platformType: "educationStudent",
        msgContent: "ok"
      };
      this.webSocket.send(JSON.stringify(msg));
      this.socketCloseTimer = setTimeout(() => {
        // 10秒未收到服务器响应关闭连接
        this.webSocket.close();
      }, 3000);
    },

    //接收消息
    onMessage(event) {
      //数据接收
      let that = this;
      let data = JSON.parse(event.data);
      let messageType = data.messageType;
      if (messageType === SocketMessageType.REJECT_SESSION.code) {
        this.$message({
          type: "error",
          message: data.msgContent
        });
        setTimeout(function() {
          that.$router.push("login");
        }, 5000);
      } else if (messageType === 10013) {
        this.unReadMessageNumber++;
      } else if (data.messageType === SocketMessageType.HEART.code) {
        clearTimeout(this.socketCloseTimer); // 清除关闭socket连接定时器
        setTimeout(() => {
          this.heartStart(); // 重置心跳
        }, 5000);
      }
    },

    /**
     * 尝试重新连接
     */
    tryConnection() {
      if (this.lockConnection) {
        return; // 重连锁，防止出现死循环
      }

      this.lockConnection = true;
      clearTimeout(this.webSocketTimer);
      this.webSocketTimer = setTimeout(() => {
        // console.log('tryConnection')
        this.initWebSocket();
        this.lockConnection = false;
      }, 10000);
    },

    onClose(event) {
      //关闭
      console.log("websocket 连接 close");
      if (this.lockConnection) {
        return;
      }
      this.tryConnection();
    },

    onError(event) {
      console.log("websocket 连接error");
      if (this.lockConnection) {
        return;
      }
      this.tryConnection();
    },

    // 退出
    logout() {
      let that = this;
      that
        .$confirm("确定退出系统吗?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
        .then(() => {
          that.logoutCommon(that);
        });
    }
  }
};
</script>
<style lang="less" scoped>
.el-dropdown-link {
  width: 50px;
  cursor: pointer;
  img {
    width: 32px;
    height: 32px;
    display: inline-block;
    vertical-align: middle;
  }
}
.header {
  width: 1200px;
  height: 100px;
  margin: 0 auto;
  position: relative;
  .el-dropdown {
    position: absolute;
    right: 0;
    top: 35px;
    height: 36px;
    line-height: 36px;
    cursor: pointer;
    .cp {
      font-size: 36px;
      color: #009cde;
      padding-right: 5px;
      vertical-align: middle;
    }
  }
  .systemtitle {
    width: 400px;
    height: 100%;
    float: left;
    overflow: hidden;
    padding-top: 20px;
    padding-bottom: 10px;
    img {
      width: 100%;
      height: 100%;
    }
  }
  .head-nav {
    position: absolute;
    right: 300px;
    top: 30px;
    display: flex;
    border: 1px solid #52565a;
    border-radius: 16px;
    overflow: visible;
    .activeitem {
      padding: 15px;
      font-size: 16px;
      color: rgb(0, 0, 0);
      position: relative;
      &:first-child {
        border-radius: 16px 0 0 16px;
      }
      &:last-child {
        border-radius: 0 16px 16px 0;
      }
      .itemli {
        width: 94px;
        position: absolute;
        left: 0;
        top: 51px;
        z-index: 111;
        display: none;
        li {
          width: 100%;
          padding: 15px;
          color: rgb(0, 0, 0);
        }
        li:hover {
          color: #52565a;
        }
      }
    }
    .active {
      background-color: #52565a;
      color: #fff;
    }
    .activeitem:hover {
      background-color: #52565a;
      color: #fff;
      cursor: pointer;
      .itemli {
        display: block;
        background-color: #fff;
        border-radius: 0 0 6px 6px;
      }
    }
  }
}
</style>
