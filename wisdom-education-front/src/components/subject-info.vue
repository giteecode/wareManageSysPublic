<template>
  <div class="subject">
    <div id="nav">
      <a @click="selectSubject(null)" :class="selectId === 0 ? 'on' : ''">全部</a>
      <a v-for="subject in studentSubjectList"
         :key="subject.id"
         :class="subject.id === selectId ? 'on' : ''"
         @click="selectSubject(subject)" >{{subject.name}}</a>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'subject-info',
    props: {


    },

    watch: {

    },

    data () {
      return {
        selectId: 0,
        studentSubjectList: []
      }

    },

    mounted() {
      this.getStudentSubjectList()
    },

    methods: {

      // 获取学员学习科目
      getStudentSubjectList () {
        this.axios.get(this.$httpApi.httpUrl('/student/subject'), {
          params: {

          }
        }).then(response => {
          this.studentSubjectList = response.data.data
        })
      },

      selectSubject (subject) {
        let subjectId = null
        if (subject) {
          this.selectId = subject.id
          subjectId = subject.id
        } else {
          this.selectId = 0
        }
        this.$emit("change", subjectId)
      }
    }
  }
</script>

<style lang="scss" scoped>
  .subject .on {
    background: rgba(204,136,0,.1);
    color: #c80;
  }

  .subject a {
    font-size: 15px;
    line-height: 40px;
    min-width: 60px;
    padding: 0 15px;
    margin-top: 10px;
    margin-right: 25px;
    float: left;
  }

  .subject a:hover {
    cursor: pointer;
    background: rgba(204,136,0,.1);
    color: #c80;
  }
</style>
