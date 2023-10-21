<template>
  <div class="container learnstyle-page">
    <el-card style="position: relative; top: 15%; min-height: 300px" >
      <el-row class="tac">
        <el-col style="width: 20%" :span="12">
          <el-menu
            :default-active="userMenuIndex"
            class="el-menu-vertical-demo">
            <el-menu-item @click="goView('userInfo', '1-1')" index="1-1">
              <i class="el-icon-s-custom"></i>
              <span slot="title">个人资料</span>
            </el-menu-item>
            <el-menu-item @click="goView('passWord', '1-2')" index="1-2">
              <i class="el-icon-key"></i>
              <span slot="title">修改密码</span>
            </el-menu-item>

            <el-menu-item @click="goView('message', '1-3')" index="1-3">
              <i class="el-icon-message"></i>
              <span slot="title">消息管理</span>
            </el-menu-item>
          </el-menu>
        </el-col>

        <el-col style="width: 80%" :span="12">
            <router-view />
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: 'userCenter',
    data () {
      return {
        tabPosition: 'left',
        userMenuIndex: '1-1'
      }
    },

    mounted() {

      let index = sessionStorage.getItem('userMenuIndex')
      if (index) {
        this.userMenuIndex = index
      } else {
        this.userMenuIndex = '1-1'
      }
    },

    methods: {

      goView (view, index) {
        sessionStorage.setItem('userMenuIndex', index)
        this.$router.push(view)
      }
    }
  }
</script>

<style scoped>

</style>
