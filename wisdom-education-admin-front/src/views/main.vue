<template>
  <div
    class="site-wrapper"
    :class="{ 'site-sidebar--fold': sidebarFold }"
    v-loading.fullscreen.lock="loading"
    element-loading-text="拼命加载中">
    <template v-if="!loading">
      <main-navbar />
      <main-sidebar />
      <div class="site-content__wrapper" :style="{ 'min-height': documentClientHeight + 'px' }">
        <main-content />
      </div>
    </template>
  </div>
</template>
<script>
  import mainNavbar from './main-navbar'
  import mainSidebar from './main-sidebar'
  import mainContent from './main-content'
  export default {
    data () {
      return {
        loading: true
      }
    },
    components: {
      mainNavbar,
      mainSidebar,
      mainContent
    },
    computed: {
      documentClientHeight: {
        get () { return this.$store.state.common.documentClientHeight },
        set (val) { this.$store.commit('common/updateDocumentClientHeight', val) }
      },
      sidebarFold: {
        get () { return this.$store.state.common.sidebarFold }
      },
      userId: {
        get () { return this.$store.state.user.id },
        set (val) { this.$store.commit('user/updateId', val) }
      },
      userName: {
        get () { return this.$store.state.user.name },
        set (val) { this.$store.commit('user/updateName', val) }
      }
    },

    mounted () {
      this.resetDocumentClientHeight()
      this.getUserInfo()
    },
    methods: {
      // 重置窗口可视高度
      resetDocumentClientHeight () {
        this.documentClientHeight = document.documentElement['clientHeight']
        window.onresize = () => {
          this.documentClientHeight = document.documentElement['clientHeight']
        }
      },
      // 获取当前管理员信息
      getUserInfo () {
        let userInfo = JSON.parse(localStorage.getItem('userInfo'))
        if (userInfo) {
          this.userName = userInfo.login_name
        }
        this.loading = false
      }
    }
  }
</script>
