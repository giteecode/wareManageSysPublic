<template>
  <div class="container learnstyle-page">
    <el-card style="position: relative; top: 15%; min-height: 300px" >
      <el-row class="tac">
        <el-col style="width: 20%" :span="12">
          <el-menu
            :default-active="courseMenuIndex"
            class="el-menu-vertical-demo">
            <el-menu-item @click="goView('myCourse', '1-1')" index="1-1">
              <i class="el-icon-s-custom"></i>
              <span slot="title">我的课程</span>
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
        courseMenuIndex: '1-1'
      }
    },

    mounted() {
      let index = sessionStorage.getItem('courseMenuIndex')
      if (index) {
        this.courseMenuIndex = index
      } else {
        this.courseMenuIndex = '1-1'
      }
    },

    methods: {

      goView (view, index) {
        sessionStorage.setItem('courseMenuIndex', index)
        this.$router.push(view)
      }
    }
  }
</script>

<style scoped>

</style>
