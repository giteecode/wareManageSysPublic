<template>
  <div class="question_type">
    <div id="nav">
      <a @click="selectQuestionType(null)" :class="selectQuestionTypeValue === 0 ? 'on' : ''">全部</a>
      <a v-for="question in questionTypeList"
         :key="question.code"
         :class="question.code === selectQuestionTypeValue ? 'on' : ''"
         @click="selectQuestionType(question)" >{{question.value}}</a>
    </div>
  </div>
</template>
<script>
    import {getDictValueByType} from '@/api/dict'
    export default {
      name: 'question-type',
      data () {
          return {
            selectQuestionTypeValue: 0,
            questionTypeList: []
          }
      },

      mounted () {
        getDictValueByType('question_type').then(response => {
          this.questionTypeList = response.data.data
        })
      },

      methods: {
        selectQuestionType (questionType) {
          let questionTypeValue = null
          if (questionType) {
            this.selectQuestionTypeValue = questionType.code
            questionTypeValue = questionType.code
          } else {
            this.selectQuestionTypeValue = 0
          }
          this.$emit('change', questionTypeValue)
        }
      }
    }
</script>

<style lang="scss" scoped>
  .question_type .on {
    background: rgba(204,136,0,.1);
    color: #c80;
  }

  .question_type a {
    font-size: 15px;
    line-height: 40px;
    min-width: 60px;
    padding: 0 15px;
    margin-top: 10px;
    margin-right: 25px;
    float: left;
  }

  .question_type a:hover {
    cursor: pointer;
    background: rgba(204,136,0,.1);
    color: #c80;
  }
</style>
