<template>
  <div class="demoList">
    <ul class="clearfix">
      <li class="typeitem" :key="index" v-for="(course, index) in courserList">
        <img @click="studyCourse(course)" :src="fileHost + course.headImg" class="image">
        <p class="title">{{course.name}}</p>
        <p class="course_detail">{{course.describe}}</p>
        <div style="margin-top: 5px;line-height:35px;">
          <i class="el-icon-user-solid">{{course.studyNumber}}人练习过</i>
          <el-button
            v-if="course.collectFlag === 1"
            style="float: right; color: red"
            @click="collectCourse(index)" icon="el-icon-star-off" type="text">已收藏</el-button>

          <el-button
            style="float: right"
            v-else
            @click="collectCourse(index)" icon="el-icon-star-off" type="text">收藏</el-button>
        </div>
      </li>
    </ul>

    <div class="page">
      <el-pagination
        v-if="totalCount > pageSize"
        @current-change="changeIndexCoursePage"
        background
        :current-page="currentPage"
        :page-size="pageSize"
        layout="prev, pager, next, jumper"
        :total="totalCount">
      </el-pagination>
    </div>
  </div>
</template>

<script>
    export default {
      name: 'course-list',
      props: {
        dataList: {
          type: Array,
          default: []
        },
        totalCount: {
          type: Number,
          default: 0
        }
      },


      watch: {
        dataList(val) {
          this.courserList = val
        }
      },

      data() {
        return {
          fileHost: this.$store.state.common.fileHost,
          currentPage: 1,
          pageSize: 10,
          courserList: []
        }
      },

      methods: {
        collectCourse(index) {
          let courseInfo = this.dataList[index]
          if (courseInfo.collectFlag === 1) {
            courseInfo.collectFlag = 0
          } else {
            courseInfo.collectFlag = 1
          }
          let params = {
            courseId: courseInfo.id,
            collectFlag: courseInfo.collectFlag
          }
          this.axios.post(this.$httpApi.httpUrl('/student/course/collect'), params)
            .then((response) => {

            })
        },

        studyCourse(course) {
          this.$store.commit('course/updateCourseInfo', course)
          this.$router.push({
            name: 'courseDetail',
          })
        },

        changeIndexCoursePage (val) {
          this.currentPage = val
        }
      }
    }
</script>

<style lang="scss" scoped>
  .demoList ul {
    width: 100%;
    box-sizing: border-box;
    margin-bottom: 100px;
  }
  .demoList{
    margin: 0 auto;
    padding-top: 30px;
  }

  .course_detail {
    padding-top: 6px;
    max-height: 48px;
    line-height: 20px;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    font-size: 14px;
    color: #93999f
  }

  .demoList li {
    box-sizing: border-box;
    width: 192px;
    min-height: 160px;
    float: left;
    margin-right: 40px;
    margin-bottom: 30px;
    text-align: left;
    img{
      cursor: pointer;
      width: 100%;
      height:108px;
      display: block;
      border-radius: 8px;
      &:hover{
        opacity: 0.7;
        /* .course_title{
           color:#ca0000;
         }*/
      }
    }
  }

  .typeitem{
    cursor: pointer;
    width: 300px;
    .title {
      padding-top: 8px;
      font-weight: bold;
      &:hover {
        cursor: pointer;
        color: red;
      }
    }
  }

  .demoList li{
    width: 30%;
  }

  .demoList  .page {
    position: relative;
    left: 0;
    bottom: 50px;
    width: 100%;
    text-align: center;
  }

  .demoList li .image {
    height: 199px;
    width: 100%;
    display: block;
  }
</style>
