<template>
  <div>
    <el-card v-for="(item, index) in questionList" style="width: 100%" :key="index" class="box_card">
      <div slot="header" class="clearfix">
        <span style="font-weight: bold">{{item.questionTypeName}}</span>
      </div>
      <div v-for="(question, itemIndex) in item.questionInfoAnswerList" :key="itemIndex" class="text item">
        <label style="font-weight: bold">{{question.questionIndex}}.</label>
        <div style="display: inline-block" v-html="question.content"></div>

        <el-row class="test-opt" style="padding-left: 20px;margin-top: 15px">
          <!-- 单选题 -->
          <template v-if="question.questionType === 1">
            <el-radio-group
              v-for="(item, index) in question.optionList"
              :key="index"
              v-model="question.studentAnswer">
              <el-radio  :disabled="true" :label="item.label">{{item.label}}. &nbsp;
                <label v-html="item.option_name"></label>
              </el-radio>
            </el-radio-group>
          </template>
        </el-row>

        <!-- 多选题 -->
        <el-row v-if="question.questionType === 2" class="option">
          <el-checkbox-group
            v-for="(item, index) in question.optionList"
            :key="index"
            v-model="question.studentAnswer">
            <el-checkbox :disabled="true" :label="item.label">{{item.label}}. &nbsp;
              <label v-html="item.option_name"></label>
            </el-checkbox>
          </el-checkbox-group>
        </el-row>

        <el-row v-if="question.questionType === 6" class="option">
            <el-radio v-model="question.studentAnswer" :disabled="true" label="1">对</el-radio>
            <el-radio v-model="question.studentAnswer" :disabled="true" label="0">错</el-radio>
        </el-row>

        <div class="text item">
          分值:  <el-tag type="success">{{question.questionMark}}分</el-tag>
        </div>

        <div class="text item">
          批改结果:
          <el-tag v-if="question.correctStatus === 1" >
            正确
          </el-tag>

          <el-tag v-else type="danger">
            {{question.correctStatus | getCorrectStatus}}
          </el-tag>
        </div>

        <template v-if="isSubjective(question.questionType)">

          <template v-if="question.studentAnswer.length > 0">
            <div class="text item">
              你的答案:
            </div>

            <div  class="text item">
              <div class="demo-image__preview">
                <el-image
                  v-for="(url, urlIndex) in question.studentAnswer"
                  :key="urlIndex"
                  style="width: 100px; height: 100px; margin-left: 6px"
                  :src="url"
                  :preview-src-list="question.studentAnswer"></el-image>
              </div>
            </div>
          </template>

          <template v-else>
            <div class="text item">
              你的答案: <el-tag type="danger">未作答</el-tag>
            </div>
          </template>
        </template>

        <template v-else>
          <div class="text item">
            你的答案: {{question.studentAnswer | parseAnswer(question.questionType)}}
          </div>
        </template>

        <div v-if="question.correctStatus !== 2"  class="text item">
          得分: <el-tag >{{question.studentMark}}分</el-tag>
        </div>

        <template v-if="question.questionType === 1
                || question.questionType === 2
                || question.questionType === 6">
          <div  class="text item">
            <label style="color: #f56c6c">正确答案:</label> {{question.answer | parseAnswer(question.questionType)}}
          </div>
        </template>

        <template v-else>
          <div class="text item">
            <label style="color: #f56c6c">正确答案:</label>
          </div>

          <div v-html="question.answer"  class="text item">

          </div>
        </template>

        <div class="text item">
          <label style="font-weight: bold">解析:</label> : <el-tag v-if="!question.analysis" type="danger">暂无解析</el-tag>
        </div>

        <div
          v-if="question.analysis"
          v-html="question.analysis" class="text item">
        </div>

        <div style="border-bottom: 1px solid #EBEEF5" class="text item">
          <span class="play" @click="playVideo(question.videoUrl)"><i class="el-icon-video-play"></i> 视频点评 </span>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
    export default {
      name: 'question',
      props: {
        groupQuestionList: {
          type: Array,
          default: []
        }
      },

      watch: {
        groupQuestionList (val) {
          this.questionList = val
          if (this.questionList.length > 0) {
            this.parseQuestion()
          }
        }
      },

      data () {
        return {
          fileHost: this.$store.state.common.fileHost,
          questionList: []
        }
      },

      mounted () {
        this.questionList = this.groupQuestionList
      },

      methods: {

        isSubjective (questionType) {
          if (questionType !== 1 && questionType !== 2 && questionType !== 6 && questionType !== 8) {
            return true
          }
          return false
        },

        parseQuestion () {
          let questionIndex = 0
          this.questionList.forEach(item => {
            let questionList = item.questionInfoAnswerList
            questionList.forEach(subItem => {
              ++questionIndex
              subItem.questionIndex = questionIndex
              if (subItem.questionType === 1 || subItem.questionType === 2) {
                let options = subItem.options
                if (options) {
                  subItem.optionList = JSON.parse(options)
                }
              }

              // 试题类型非单选和判断题， 解析答案数组
              if (subItem.questionType !== 1 && subItem.questionType !== 6 && subItem.questionType !== 8) {
                let studentAnswerStr = subItem.studentAnswer
                let studentAnswerArray = []
                if (studentAnswerStr) {
                  studentAnswerStr.split(',').forEach(value => {
                    if (value && subItem.questionType !== 2) {
                      studentAnswerArray.push(this.fileHost + value)
                    } else {
                      // 多选题
                      studentAnswerArray.push(value)
                    }
                  })
                  subItem.studentAnswer = studentAnswerArray
                }
              }
            })
          })
        }
      },

      filters: {
        getCorrectStatus (val) {
          if (val === 0) {
            return '错误'
          } else if (val === 1) {
            return '正确 '
          } else if(val === 2) {
            return '待批改'
          } else {
            return '已批改'
          }
        },

        parseAnswer (answer, questionType) {
          if (answer === 1 || answer === '1') {
            return '对'
          } else if (answer === 0 || answer === '0') {
            return '错'
          } else if (questionType === 2 && answer instanceof Array) {
            let answerValue = ''
            answer.forEach(value => {
              answerValue += value + ','
            })
            return answerValue.substr(0, answerValue.length - 1)
          }
          return answer
        }
      }
    }
</script>

<style lang='scss' scoped>

  .option {
    margin: 20px;
  }
  .text {
    font-size: 14px;
  }
  .play{display: inline-block;height: 30px;line-height: 30px;padding-left:10px;color: red;cursor: pointer;}
  .item {
    padding: 6px 0;
  }

  /*  .box-card {
      width: 480px;
    }*/
  .el-row {
    margin-bottom: 20px;
  &:last-child {
     margin-bottom: 0;
   }
  }
  .el-col {
    border-radius: 4px;
  }
  .el-radio-button__inner, .el-radio-group {
    padding-top: 20px !important;
    display: block !important;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
    padding: 5px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
