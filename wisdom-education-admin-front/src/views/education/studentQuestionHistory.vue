<template>
    <div class="main">
      <el-card class="box-card">
        <div style="height: 60px;" class="text item">
           <div style="font-size: 22px;font-weight: bold; text-align: center">{{studentExamInfo.testPaperInfoName}}({{studentExamInfo.paperMark}}分)</div>
           <div style="font-size: 20px; position: relative; top: 20%; text-align: center ">
             <span style="margin-left: 15px;">学科: <label style="text-decoration:underline">{{studentExamInfo.subjectName}}</label></span>
             <span style="margin-left: 15px;">得分: <label style="text-decoration:underline; font-size: 24px; color: red; font-weight: bold">{{studentExamInfo.studentMark}}分</label></span>
             <span style="margin-left: 15px;">姓名: <label style="text-decoration:underline">{{studentExamInfo.studentName}}</label></span>
           </div>
        </div>
      </el-card>


      <el-card class="box-card">

        <div slot="header" class="clearfix">
          <span style="font-weight: bold; font-size: 17px">{{currentQuestion.questionTypeName}} ({{currentQuestion.questionMark}}分)</span>
          <el-button
            v-if="!lookQuestionAnswerFlag"
            style="float: right;"
            size="medium"
            @click="commitQuestion"
            type="primary">提交并算分</el-button>
        </div>
        <div  class="text item">
          <label style="font-weight: bold">{{currentQuestion.questionIndex}}.</label>
          <div style="display: inline-block" v-html="currentQuestion.content"></div>
          <div>
            <!-- 单选题  -->
            <template v-if="currentQuestion.questionType === 1">
              <el-radio
                v-for="(item, index) in currentQuestion.optionList"
                :key="index"
                :disabled="true"
                v-model="currentQuestion.studentAnswer" :label="item.label">{{item.label}}.
                <label v-html="item.option_name"></label>
              </el-radio>
            </template>

            <!-- 多选题  -->
            <template v-else-if="currentQuestion.questionType === 2">
              <el-checkbox-group
                v-for="(item, index) in currentQuestion.optionList"
                :key="index"

                v-model="currentQuestion.studentAnswer">
                <el-checkbox :disabled="true" :label="item.label">{{item.label}}.
                  <label v-html="item.option_name"></label>
                </el-checkbox>
              </el-checkbox-group>
            </template>

            <template v-else-if="currentQuestion.questionType === 6">
              <el-radio :disabled="true" v-model="currentQuestion.studentAnswer"  label="1">对</el-radio>
              <el-radio :disabled="true" v-model="currentQuestion.studentAnswer"  label="0">错</el-radio>
            </template>
          </div>
        </div>
        <div  class="text item">
          <h3  style="font-size: 20px;color: black;font-weight: 700;">
            <i class="el-icon-star-off" style="font-size: 20px; color: #239676; float:left;font-weight: 500;"></i>&nbsp;&nbsp;正确答案
          </h3>
          <template v-if="currentQuestion.questionType === 6">
            <div v-if="currentQuestion.answer === '1'" class="answer-detail">对</div>
            <div v-else class="answer-detail">错</div>
          </template>
          <template v-else>
            <div style="margin-left: 30px" v-html="currentQuestion.answer"></div>
          </template>
        </div>

        <div  class="text item" v-if="currentQuestion.questionType !== 1
        && currentQuestion.questionType !== 2 && currentQuestion.questionType !== 6">
          <h3  style="font-size: 20px;color: black;font-weight: 700;">
            <i class="el-icon-edit-outline" style="font-size: 20px; color: #239676; float:left;font-weight: 500;"></i>&nbsp;&nbsp;学员答案
          </h3>
          <div class="answer-detail">

            <div v-if="currentQuestion.questionType === 8" class="demo-image">
              <div class="block">
                <el-input :rows="3" type="textarea" v-model="currentQuestion.studentAnswer"/>
              </div>
            </div>

            <div v-if="currentQuestion.studentAnswerArray && currentQuestion.studentAnswerArray.length > 0" class="demo-image">
              <div class="block">
                <el-image
                  v-for="(answer, index) in currentQuestion.studentAnswerArray"
                  :key="index"
                  style="width: 100px; height: 100px; margin-left: 15px"
                  :preview-src-list="currentQuestion.studentAnswerArray"
                  :src="answer"></el-image>
              </div>
            </div>

            <div v-else>
              <el-tag type="danger">学员未作答</el-tag>
            </div>
          </div>
          <div slot="header" class="clearfix">

            <el-form ref="form" label-width="120px">
              <el-form-item label="得分:">
                <el-input-number v-model="currentQuestion.studentMark" :min="0" :max="100" label="得分"></el-input-number>&nbsp;&nbsp;分
              </el-form-item>

              <el-form-item label="评语:">
                <el-input v-model="currentQuestion.comment" clearable :rows="5" type="textarea"></el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>

        <div v-if="currentQuestion.questionType !== 1
        && currentQuestion.questionType !== 2 && currentQuestion.questionType !== 6 && !lookQuestionAnswerFlag">
          <el-form label-width="120px">
            <el-form-item label="是否加入错题本:">
              <el-radio v-model="currentQuestion.errorQuestionFlag" :label="1">是</el-radio>
              <el-radio v-model="currentQuestion.errorQuestionFlag" :label="0">否</el-radio>
            </el-form-item>
          </el-form>
        </div>
      </el-card>

      <el-row>
        <el-button-group class="btngroup">
          <el-button
            style="margin-right: 50px"
            icon="el-icon-back"
            @click="goBack"
            size="small">返回上一页</el-button>
          <el-button
            icon="el-icon-arrow-left"
            v-if="currentQuestionPage !==  0"
            @click="lastQuestion"
            size="small">上一题</el-button>
          <el-button
            style="margin-left: 50px"
            v-if="totalCount > 1 && currentQuestionPage !== totalCount - 1"
            @click="nextQuestion"
            type="primary" size="small">下一题<i class="el-icon-arrow-right el-icon--right"></i> </el-button>
        </el-button-group>
      </el-row>
    </div>
</template>

<script>
    // eslint-disable-next-line import/first
    import { mapGetters } from 'vuex'
    export default {
      name: 'studentQuestionHistory',
      data () {
        return {
          lookQuestionAnswerFlag: false, // 是否查看试卷详情
          dialogImageUrl: '',
          fileHost: this.$http.getFileHost(),
          studentId: '',
          studentQuestionList: [],
          currentQuestion: {},
          currentQuestionPage: 0,
          loading: true,
          testPaperInfoId: '',
          totalCount: 0,
          dialogAnswerImgVisible: false
        }
      },

      computed: {
        ...mapGetters({
          studentExamInfo: 'map/getStudentExamInfo'
        })
      },

      mounted () {
        this.studentId = this.studentExamInfo.studentId
        this.testPaperInfoId = this.studentExamInfo.testPaperInfoId
        this.lookQuestionAnswerFlag = this.studentExamInfo.lookQuestionAnswerFlag
        this.getStudentQuestionList()
      },

      methods: {

        getStudentQuestionList () {
          this.axios.get(this.$http.httpUrl('/system/exam/' + this.studentId + '/' + this.studentExamInfo.examInfoId + '/question'))
            .then(response => {
              let questionList = []
              let examQuestionItemResponseList = response.data.data.questionGroupItemResponseList
              let questionIndex = 0
              examQuestionItemResponseList.forEach(item => {
                let examQuestionAnswerList = item.questionInfoAnswerList
                examQuestionAnswerList.forEach(question => {
                  questionIndex++
                  question.questionIndex = questionIndex
                  question.questionInfoId = question.id
                  if (question.options) {
                    question.optionList = JSON.parse(question.options)
                  }
                  if (question.questionType !== 1 && question.questionType !== 6) {
                    let studentAnswer = []
                    if (question.questionType !== 2) {
                      question.studentAnswer.split(',').forEach(answer => {
                        if (answer) {
                          studentAnswer.push(this.fileHost + answer)
                        }
                      })
                      question.studentAnswerArray = studentAnswer
                    } else {
                      question.studentAnswer.split(',').forEach(answer => {
                        if (answer) {
                          studentAnswer.push(answer)
                        }
                      })
                      question.studentAnswer = studentAnswer
                    }
                  }

                  if (question.questionType !== 1 && question.questionType !== 2 && question.questionType !== 6) {
                    question.errorQuestionFlag = 0 // 默认不加入学员错题本
                  }
                  questionList.push(question)
                })
              })

              this.studentQuestionList = questionList

              this.totalCount = questionList.length
              if (this.totalCount > 0) {
                this.currentQuestion = this.studentQuestionList[0]
              }
              this.loading = false
            })
        },

        goBack () {
          if (!this.lookQuestionAnswerFlag) {
            this.$confirm('试卷还未批改，确定离开吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.$router.go(-1)
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消操作'
              })
            })
          } else {
            this.$router.go(-1)
          }
        },

        commitQuestion () {
          this.$confirm('确定提交吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            let questionAnswerList = []
            this.studentQuestionList.forEach(item => {
              if (item.correctStatus === 2) { // 待批改单试题
                let studentAnswerStr = ''
                if (item.studentAnswer instanceof Array) {
                  if (item.studentAnswer.length > 0) {
                    item.studentAnswer.forEach(answer => {
                      studentAnswerStr += answer + ','
                    })
                    studentAnswerStr = studentAnswerStr.substr(0, studentAnswerStr.length - 1)
                  }
                  item.studentAnswer = studentAnswerStr
                }
                questionAnswerList.push(item)
              }
            })

            this.axios.post(this.$http.httpUrl('/system/exam/correct'), {
              studentId: this.studentId,
              examInfoId: this.studentExamInfo.examInfoId,
              teacherCorrectFlag: true,
              questionAnswerList: questionAnswerList
            }).then(response => {
              if (response.data.code === 1) {
                this.$message.success(response.data.message)
                this.$router.go(-1)
              }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消操作'
            })
          })
        },

          // 下一题
        nextQuestion () {
          this.currentQuestionPage++
          this.currentQuestion = this.studentQuestionList[this.currentQuestionPage]
        },

        lookAnswerImg (url) {
          this.dialogAnswerImgVisible = true
          this.dialogImageUrl = url
        },

          // 上一题
        lastQuestion () {
          this.currentQuestionPage--
          this.currentQuestion = this.studentQuestionList[this.currentQuestionPage]
        }
      },

      filters: {

      }
    }
</script>

<style scoped>
  .el-input {
    width: 15%;
    text-align: center;
  }
  .el-row {
         margin-top: 25px;
       text-align: center;
     }

    .main >>> p {
      display: inline-block;
    }
    .text {
      font-size: 14px;
    }

    .item {
      margin-bottom: 18px;
    }
    .clearfix:before, .clearfix:after {
      display: table;
      content: "";
      margin-top: 10px;
    }
    .clearfix:after {
      clear: both
    }

    .answer-detail {
      margin-left: 40px;
    }
    .main .box-card {
      width: 100%;
      margin-top: 10px;
    }
</style>
