<template>
  <div class="home">
    <div class="banner">
      <swiper :options="swiperOption">
        <template>
          <swiper-slide v-for="(image, index) in indexLogo" :key="index">
            <!--<img :src="@/assets/img/education1.png" alt="" width="100%" height="500">-->
            <img :src="image" width="100%" height="500" />
          </swiper-slide>
        </template>
        <!--        <template v-else>
          <swiper-slide >
            <img src="@/assets/img/education1.png" alt="" width="100%" height="500">
          </swiper-slide>
          <swiper-slide>
            <img src="@/assets/img/education3.jpg" alt="" width="100%" height="500">
          </swiper-slide>
        </template>-->

        <div class="swiper-pagination" slot="pagination"></div>
      </swiper>
    </div>

    <div class="container">
      <div class="title">
        <h2>推荐课程</h2>
      </div>

      <div class="demoList">
        <ul class="clearfix">
          <li
            class="typeitem"
            :key="index"
            v-for="(course, index) in recommendCourseList"
          >
            <el-card>
              <img
                @click="studyCourse(course)"
                :src="fileHost + course.headImg"
                class="image"
              />
              <p class="title">{{ course.name }}</p>
              <p class="course_detail">{{ course.describe }}</p>
              <div style="margin: 5px;line-height:35px;">
                <i class="el-icon-user-solid">{{ course.studyNumber }}人学习</i>

                <el-button
                  v-if="course.collectFlag === 1"
                  style="float: right; color: red"
                  @click="collectCourse(index)"
                  icon="el-icon-star-off"
                  type="text"
                  >已收藏</el-button
                >

                <el-button
                  style="float: right"
                  v-else
                  @click="collectCourse(index)"
                  icon="el-icon-star-off"
                  type="text"
                  >收藏</el-button
                >
              </div>
            </el-card>
          </li>
        </ul>
      </div>
    </div>

    <!--    <div class="container">
      <div class="title" style="margin: 50px 0">
        <el-divider class="wow bounceInRight faster">
          <h2 class="wow bounceInRight fast">今日考试</h2>
        </el-divider>
      </div>

      <div class="demoList">
        <ul class="clearfix">
          <li class="typeitem" :key="index" v-for="(course, index) in recommendCourseList">
            <el-card>
              <img @click="studyCourse(course)" :src="fileHost + course.headImg" class="image">
              <p class="title">{{course.name}}</p>
              <p class="course_detail">{{course.describe}}</p>
              <div style="margin: 5px;line-height:35px;">
                <i class="el-icon-user-solid">{{course.studyNumber}}人学习</i>

                <el-button
                  v-if="course.collectFlag === 1"
                  style="float: right; color: red"
                  @click="collectCourse(index)" icon="el-icon-star-off" type="text">已收藏</el-button>

                <el-button
                  style="float: right"
                  v-else
                  @click="collectCourse(index)" icon="el-icon-star-off" type="text">收藏</el-button>
              </div>
            </el-card>
          </li>
        </ul>
      </div>
    </div>-->
  </div>
</template>

<script>
import { swiper, swiperSlide } from "vue-awesome-swiper";
import "swiper/dist/css/swiper.css";
import { mapGetters } from "vuex";
export default {
  components: {
    swiper,
    swiperSlide
  },
  data() {
    return {
      indexLogo: [],
      carouselImageList: [],
      fileHost: this.$store.state.common.fileHost,
      currentPage: 1,
      pageSize: 20,
      totalCount: 0,
      recommendCourseList: [],
      swiperOption: {
        pagination: {
          el: ".swiper-pagination"
        },
        autoplay: {
          disableOnInteraction: false,
          delay: 3000
        },
        autoplayDisableOnInteraction: false,
        loop: true
      }
    };
  },

  computed: {
    ...mapGetters({
      webSiteConfig: "common/getWebSiteConfig"
    })
  },

  mounted() {
    this.getRecommendCourseList();
    this.getIndexLogo();
  },

  methods: {
    collectCourse(index) {
      let courseInfo = this.recommendCourseList[index];
      if (courseInfo.collectFlag === 1) {
        courseInfo.collectFlag = 0;
      } else {
        courseInfo.collectFlag = 1;
      }
      let params = {
        courseId: courseInfo.id,
        collectFlag: courseInfo.collectFlag
      };
      this.axios
        .post(this.$httpApi.httpUrl("/student/course/collect"), params)
        .then(response => {});
    },

    getIndexLogo() {
      this.axios
        .get(this.$httpApi.httpUrl("/student/advertisement/place/1"), {
          params: {}
        })
        .then(response => {
          let dataList = response.data.data;
          if (dataList) {
            this.indexLogo = [];
            dataList.forEach(item => {
              this.indexLogo.push(item.imgUrl);
            });
          }
        });
    },

    studyCourse(course) {
      this.$store.commit("course/updateCourseInfo", course);
      this.$router.push({
        path: "/courseDetail/" + course.id
      });
    },

    getRecommendCourseList() {
      this.axios
        .get(this.$httpApi.httpUrl("/student/courseInfo/recommend"))
        .then(response => {
          this.recommendCourseList = response.data.data;
        });
    },

    changeIndexCoursePage(val) {
      this.currentPage = val;
    }
  }
};
</script>
<style>
.demoList .el-card__body {
  padding: 0px !important;
}
</style>
<style lang="scss" scoped>
h2 {
  font-size: 36px;
  text-align: center;
  margin: 40px 0;
  font-weight: normal;
}
h3 {
  font-size: 18px;
  text-align: center;
  color: #737373;
  margin: 40px 0;
  font-weight: normal;
}
.el-divider__text,
.el-link {
  font-weight: 500;
  font-size: 45px;
}

.demoList ul {
  width: 100%;
  box-sizing: border-box;
  margin-bottom: 100px;
}
.demoList {
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
  color: #93999f;
}

.demoList li {
  box-sizing: border-box;
  width: 192px;
  min-height: 160px;
  float: left;
  margin-right: 40px;
  margin-bottom: 30px;
  text-align: left;
  img {
    cursor: pointer;
    width: 100%;
    height: 108px;
    display: block;
    border-radius: 8px;
    &:hover {
      opacity: 0.7;
      /* .course_title{
          color:#ca0000;
        }*/
    }
  }
}

.typeitem {
  cursor: pointer;
  width: 300px;
  .title {
    padding: 8px;
    font-weight: bold;
    &:hover {
      cursor: pointer;
      color: red;
    }
  }
}

.demoList li {
  width: 30%;
}

.demoList .page {
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

@keyframes turn-over {
  to {
    transform: rotateX(-180deg);
  }
}
.home {
  overflow: hidden;
}
</style>
