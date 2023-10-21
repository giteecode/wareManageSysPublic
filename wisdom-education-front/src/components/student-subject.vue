<template>
  <div class="subjectinfo">
    <div class="userinfo">
      <ul class="userul">
        <li><span>姓名：</span><span>{{userInfo.name}}</span></li>
        <li><span>年级：</span><span>{{userInfo.gradeName}}</span></li>
      </ul>
    </div>

    <div class="subinfo">
      <div class="subbox">
        <ul class="clearfix">

          <template v-if="noData">
            <div class="boxno" style="width:100%;height:100%;display: flex;align-items: center;justify-content: center;">
              <img src="/static/image/noData.jpg" alt="" style="display: block">
            </div>
          </template>

          <template v-else v-for="subject in studentSubjectList">
            <li class="typeitem" @click="goToUrl(subject)">
              <img :src="fileHost + subject.cover_img" class="image"><p class="teacher">
              <span class="subject">{{subject.name}}</span>
            </p>
            </li>
          </template>

        </ul>
      </div>
    </div>
  </div>
</template>

<script>
    export default {
        name: 'student-subject',
        props: {
          // viewName: {
          //   type: String,
          //   required: true
          // }
        },

        watch: {
          // viewName (val) {
          //   this.goPath = val
          // }
        },

        data () {
          return {
            fileHost: this.$store.state.fileHost,
            userInfo: {},
            firstSubjectName: '',
            noData: false,
            studentSubjectList: []
          }
        },

        mounted () {
          this.userInfo = JSON.parse(localStorage.getItem('userInfo'))
          this.getStudentSubjectList()
        },

        methods: {

          goBack () {
            this.$router.go(-1)
          },

          goToUrl (subject) {
            this.$emit('click', subject)
          },

          // 获取学员学习科目
          getStudentSubjectList () {
            let that = this
            that.axios.get(that.$httpApi.httpUrl('/student/subject/getSubjectByGradeType'), {
              params: {

              }
            }).then(function (response) {
              that.studentSubjectList = response.data.data
              if (that.studentSubjectList.length === 0) {
                this.noData = true
              } else {
                that.firstSubjectName = that.studentSubjectList[0].name
              }
            })
          }
        }
    }
</script>

<style lang='scss' scoped>
  .typeitem .subject:hover {
    color: red;
    font-weight: bold;
  }
  .back:hover {
    cursor: pointer;
  }

  .subpage{
    .banner{
      width: 100%;
      height: 360px;
      background: url('/static/image/bannerpic.png') no-repeat;
      background-size:100% 100%;
      background-position: center;
    }
    .subjectinfo{
      width: 1200px;
      margin: 0 auto;
      background-color: rgba(100, 100, 100, 0.82);
      padding: 10px;
      padding-left: 0;
      min-height: 500px;
      display: flex;
      margin-top: 20px;
      margin-bottom: 20px;
      .userinfo{
        width: 200px;
        height: 100%;
        .userul{
          width: 100%;
          color: #fff;

          padding-top: 20px;
          li{
            height: 60px;
            line-height: 60px;
            border-bottom:1px dashed #fff;
            padding-left: 20px;
            &:last-child{border:none;line-height: 40px;}
            .text2{text-indent: 2em;line-height: 30px;}
          }
        }
      }
      .subinfo{
        flex: 1;
        height: 100%;
        background-color: #fff;
        padding: 10px;
        .back{
          width: 100%;
          text-align: right;
          color:#ca0000;
          cursor: pointer;
        }
        .subbox{
          width: 100%;
          display: flex;
          min-height: 500px;

        }
      }

    }
  }

  .typeitem:hover {
    cursor: pointer;
    /* box-shadow: #e3e3e3 0px 0px 10px;*/
  }

  .subbox ul {
    width: 100%;
    box-sizing: border-box;
    padding: 10px;
  }
  .subbox{
    width: 100%;
    box-sizing: border-box;
  }
  .subbox li {
    box-sizing: border-box;
    width: 30%;
    min-height: 240px;
    float: left;
    margin-left: 15px;
    margin-right: 15px;
    margin-bottom: 30px;
    border-left: 1px solid #eaeceb;
    border-right: 1px solid #eaeceb;
    box-shadow: 0 2px 3px #b2b2b2;
    text-align: center;
    img{
      height: 199px;
      width: 100%;
      display: block;
    }
    p{line-height: 50px;}
  }
</style>
