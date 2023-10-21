<template>
  <aside class="site-sidebar" :class="'site-sidebar--' + sidebarLayoutSkin">
    <div class="site-sidebar__inner">
      <el-menu
        :default-active="menuActiveName || 'home'"
        :collapse="sidebarFold"
        :collapseTransition="false"
        class="site-sidebar__menu">
        <el-menu-item index="home" @click="$router.push({ name: 'home' })">
          <icon-svg name="shouye" class="site-sidebar__menu-icon"></icon-svg>
          <span slot="title">首页</span>
        </el-menu-item>

        <el-submenu v-for="(menu, parentIndex) in menuList" :key="parentIndex" :index="parentIndex + ''">
          <template slot="title">
            <icon-svg :name="menu.icon || ''" class="site-sidebar__menu-icon"></icon-svg>
            <span>{{ menu.label }}</span>
          </template>
          <el-menu-item
              v-if="menu.children.length > 0"
              v-for="(subMenu, index) in menu.children" :key="index"
              :index="subMenu.url"
              @click="$router.push(subMenu.url)">
            <icon-svg :name="subMenu.icon || ''" class="site-sidebar__menu-icon"></icon-svg>
            <span>{{subMenu.label}} </span>
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </div>
  </aside>
</template>

<script>
  import { isURL } from '@/utils/validate'
  export default {
    data () {
      return {
        dynamicMenuRoutes: []
      }
    },
    computed: {
      sidebarLayoutSkin: {
        get () { return this.$store.state.common.sidebarLayoutSkin }
      },
      sidebarFold: {
        get () { return this.$store.state.common.sidebarFold }
      },
      menuList: {
        get () { return this.$store.state.common.menuList },
        set (val) { this.$store.commit('common/updateMenuList', val) }
      },
      menuActiveName: {
        get () { return this.$store.state.common.menuActiveName },
        set (val) { this.$store.commit('common/updateMenuActiveName', val) }
      },
      mainTabs: {
        get () { return this.$store.state.common.mainTabs },
        set (val) { this.$store.commit('common/updateMainTabs', val) }
      },
      mainTabsActiveName: {
        get () { return this.$store.state.common.mainTabsActiveName },
        set (val) { this.$store.commit('common/updateMainTabsActiveName', val) }
      }

    },

    watch: {
     // '$route':'routeHandle'（监听器后面直接跟方法名）
      '$route' () {
        this.routeHandle(this.$route)
      }
    },

    created () {
      let userInfo = JSON.parse(localStorage.getItem('userInfo'))
      this.menuList = userInfo.menuList
      this.dynamicMenuRoutes = JSON.parse(sessionStorage.getItem('dynamicMenuRoutes'))
      this.routeHandle(this.$route)
    },

    methods: {

      removeTabHandle (tabName) {
        this.mainTabs = this.mainTabs.filter(item => item.name !== tabName)
        if (this.mainTabs.length >= 1) {
          // 当前选中tab被删除
          if (tabName === this.mainTabsActiveName) {
            this.$router.push({ name: this.mainTabs[this.mainTabs.length - 1].name }, () => {
              this.mainTabsActiveName = this.$route.name
            })
          }
        } else {
          this.menuActiveName = ''
          this.$router.push({ name: 'home' })
        }
      },

      // 路由操作
      routeHandle (route) {
        if (route.meta.isTab) {
          // tab选中, 不存在先添加
          let tab = this.mainTabs.filter(item => item.name === route.name)[0]
          if (!tab) {
            if (route.meta.isDynamic) {
              route = this.dynamicMenuRoutes.filter(item => item.name === route.name)[0]
              if (!route) {
                return console.error('未能找到可用标签页!')
              }
            }
            tab = {
              menuId: route.path, // route.meta.menuId || route.path,
              name: route.name,
              title: route.meta.title,
              type: isURL(route.meta.iframeUrl) ? 'iframe' : 'module',
              iframeUrl: route.meta.iframeUrl || ''
            }
            this.mainTabs = this.mainTabs.concat(tab)
            this.menuActiveName = tab.menuId + ''
            this.mainTabsActiveName = tab.name
          } else {
            this.mainTabs = this.mainTabs.filter(item => item.name !== tab.name)
            tab = {
              menuId: route.path, // route.meta.menuId || route.path,
              name: route.name,
              title: route.meta.title,
              type: isURL(route.meta.iframeUrl) ? 'iframe' : 'module',
              iframeUrl: route.meta.iframeUrl || ''
            }
            this.menuActiveName = tab.menuId + ''
            this.mainTabsActiveName = tab.name
            this.$nextTick(() => {
              this.mainTabs = this.mainTabs.concat(tab)
              this.$router.push({ name: tab.name })
            })
          }
        }
      }
    }
  }
</script>
