<template>
    <div class="userinfo">
      <el-menu
        @open="openMenu"
        @close="closeMenu"
        :default-active="defaultActive"
        class="el-menu-vertical-demo"
        :background-color="backGroundColor"
        :text-color="textColor"
        :active-text-color="activeTextColor">
        <tree-menu @clickMenu="clickMenu" :menuList="courseTreeList"></tree-menu>
      </el-menu>
    </div>
</template>

<script>
    import treeMenu from './tree-menu'
    export default {
      name: 'course-menu',
      components: {treeMenu},
      props: {
        defaultActive: {
          type: String,
          default: '1-1'
        },

        initData: {
          type: Boolean,
          default: false
        },

        subjectId: {
          type: Number, String
        },

        backGroundColor: {
          type: String,
        },

        textColor: {
          type: String,
        },

        activeTextColor: {
          type: String,
        }
      },

      watch: {
        subjectId (val) {
          this.selectSubjectId = val
          this.getCourseList()
        }
      },

      data () {
        return {
          courseTreeList: [],
          selectSubjectId: '',
        }

      },

      mounted() {
        if (this.initData) {
          this.getCourseList()
        }
      },

      methods: {
        getCourseList () {
          let that = this
          that.axios.get(that.$httpApi.httpUrl('/student/course/getCourseByGradeType'), {
            params: {
                subjectId: that.selectSubjectId,
            }
          }).then(function (response) {
            that.courseTreeList = response.data
          //  console.log('that.courseTreeList ：' + that.courseTreeList)
          })
        },

        openMenu (key, keyPath) {
          this.$emit('openMenu', key, keyPath)
        },

        closeMenu (key, keyPath) {
          this.$emit('closeMenu', key, keyPath)
        },

        clickMenu (val) {
          this.$emit('click', val)
        }
      }
    }
</script>

<style lang="scss" scoped>
  .el-menu {
    border-right: none;
  }
  .userinfo {
    width: 245px !important;
    height: 100%;
    height: 540px;
    overflow: auto;
    .userul {
      width: 100%;
      color: #fff;
      padding-left: 20px;
      padding-top: 20px;

      li {
        height: 45px;

        .text2 {
          text-indent: 2em;
          line-height: 30px;
        }
      }
    }
  }
</style>
