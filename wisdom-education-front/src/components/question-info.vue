<template>
  <div v-if="questionTotal === 0"></div>
  <div v-else>
    <div class="testinfo">
      <div class="title3" v-if="showNumberTitle">
        <span class="tops">第 {{ currentQuestionPage + 1 }} 题 </span>
      </div>

      <div>
        <div
          style="float: left; text-align: left; line-height: 40px; width: 50%"
        >
          <h3>
            {{ currentQuestion.questionType | getQuestionTypeName }} ({{
              currentQuestion.mark
            }}分)
          </h3>
        </div>
        <div style="float: right;line-height: 40px; width: 50%">
          <el-button
            @click="openAnswerFlag = true"
            type="primary"
            style="position: relative;  left: 348px;"
            round
            >答题卡</el-button
          >
        </div>
      </div>

      <div class="testbox">
        <label style="font-size: 20px;">{{ currentQuestionPage + 1 }}.</label>
        <div
          style="float: right; margin-left: 10px"
          v-html="currentQuestion.content"
        ></div>
      </div>

      <el-dialog title="答题卡" :visible.sync="openAnswerFlag" width="30%">
        <template v-for="(question, index) in questionAnswerDataList">
          <el-button
            v-if="hasAnswerQuestion(question.studentAnswer)"
            type="primary"
            :key="++index"
            size="medium"
            @click="selectQuestion(index)"
            circle
            >{{ index }}</el-button
          >
          <el-button
            v-else
            :key="++index"
            size="medium"
            @click="selectQuestion(index)"
            circle
            >{{ index }}</el-button
          >
        </template>
      </el-dialog>

      <!-- 试题选项 -->
      <el-row class="test-opt" style="padding-left: 20px;margin-top: 15px">
        <!-- 单选题 -->
        <template v-if="currentQuestion.questionType === 1">
          <el-radio-group
            v-for="(item, index) in currentQuestion.optionList"
            :key="index"
            v-model="currentQuestion.studentAnswer"
          >
            <el-radio :label="item.label"
              >{{ item.label }}. &nbsp;
              <label v-html="item.option_name"></label>
            </el-radio>
          </el-radio-group>
        </template>

        <!-- 多选题 -->
        <template v-if="currentQuestion.questionType === 2">
          <el-checkbox-group
            v-for="(item, index) in currentQuestion.optionList"
            :key="index"
            v-model="currentQuestion.studentAnswer"
          >
            <el-checkbox :label="item.label"
              >{{ item.label }}. &nbsp;
              <label v-html="item.option_name"></label>
            </el-checkbox>
          </el-checkbox-group>
        </template>

        <!-- 判断题 -->
        <template v-if="currentQuestion.questionType === 6">
          <el-radio v-model="currentQuestion.studentAnswer" label="1"
            >对</el-radio
          >
          <el-radio v-model="currentQuestion.studentAnswer" label="0"
            >错</el-radio
          >
        </template>

        <template v-if="currentQuestion.questionType === 8">
          <el-input
            style="width: 80%"
            type="textarea"
            :rows="5"
            v-model="currentQuestion.studentAnswer"
            label="1"
            >对</el-input
          >
        </template>
      </el-row>
    </div>

    <div class="btnbox">
      <el-button
        type="danger"
        @click="openWritingBoard"
        v-show="
          currentQuestion.questionType !== 1 &&
            currentQuestion.questionType !== 2 &&
            currentQuestion.questionType !== 6
        "
        round
        >开始答题
      </el-button>

      <el-button
        type="warning"
        v-if="currentQuestionPage !== 0"
        round
        @click="changeQuestionIndex(-1)"
      >
        <i class="el-icon-arrow-left"></i>
        上一题
      </el-button>

      <el-button
        v-if="questionTotal > 1 && currentQuestionPage !== questionTotal - 1"
        type="warning"
        @click="changeQuestionIndex(1)"
        round
        >下一题 <i class="el-icon-arrow-right"></i
      ></el-button>

      <el-button type="danger" class="btn" round @click="commitQuestion"
        >{{ commitButtonText }}
      </el-button>
    </div>

    <div
      v-if="
        currentQuestion.questionType !== 1 &&
          currentQuestion.questionType !== 2 &&
          currentQuestion.questionType !== 6 &&
          currentQuestion.questionType !== 8
      "
    >
      <div style="width: 150px; margin-left: 30px;">
        <h3
          class="el-icon-s-promotion"
          style="font-size: 20px; line-height:30px;color: #239676; float:left;font-weight: 500;"
        >
          &nbsp;&nbsp;
        </h3>
        <h3 style="font-size: 20px;color: black;font-weight: 700;">你的答案</h3>
      </div>
      <div style="padding-left: 30px; padding-top: 30px">
        <template>
          <ul
            class="el-upload-list el-upload-list--picture-card"
            style="display: inline-block"
          >
            <li
              v-for="(enclosure, index) in currentQuestion.studentAnswer"
              :key="index"
              class="el-upload-list__item is-success"
            >
              <img
                :src="fileUrl + enclosure"
                alt=""
                class="el-upload-list__item-thumbnail"
              />
              <label class="el-upload-list__item-status-label">
                <i class="el-icon-upload-success el-icon-check"></i>
              </label>
              <span class="el-upload-list__item-actions">
                <span
                  class="el-upload-list__item-preview"
                  @click="lookAnswerImg(fileUrl + enclosure)"
                >
                  <i class="el-icon-zoom-in"></i>
                </span>

                <span
                  class="el-upload-list__item-delete"
                  @click="deleteAnswerImg(enclosure)"
                >
                  <i class="el-icon-delete"></i>
                </span>
              </span>
            </li>
            <el-dialog :visible.sync="dialogAnswerImgVisible">
              <img width="100%" :src="dialogImageUrl" alt="" />
            </el-dialog>
          </ul>
        </template>

        <template>
          <el-upload
            :action="uploadAction"
            :headers="headers"
            :before-upload="beforeUploadAnswerImg"
            :on-success="uploadAnswerImgSuccess"
            list-type="picture-card"
            :show-file-list="false"
          >
            <i class="el-icon-plus"></i>
            <div slot="tip" class="el-upload__tip">
              答案附件上传(只能上传jpg/png文件，且不超过1M)
            </div>
          </el-upload>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
import { getDictValueByType } from "../api/dict";
let thisPage = null;
export default {
  name: "question-info",
  props: {
    // 试题及试题答案列表
    questionInfoAnswerList: {
      type: Array,
      default() {
        return [];
      }
    },

    showNumberTitle: {
      type: Boolean,
      default: true
    },

    timeOutFlag: {
      type: Boolean,
      default: false
    },

    commitButtonText: {
      type: String,
      default: "提 交"
    }
  },

  watch: {
    questionInfoAnswerList(val) {
      this.questionAnswerDataList = val;
      this.questionTotal = this.questionAnswerDataList.length;
      this.currentQuestion = this.questionAnswerDataList[0];
      this.parserQuestion();
    },

    timeOutFlag(val) {
      if (val) {
        this.commitQuestion(); // 时间结束自动提交试卷
      }
    }
  },

  data() {
    return {
      headers: {
        Authorization: this.$store.state.user.token,
        Platform: "educationStudent"
      },
      id: "",
      openAnswerFlag: false,
      sumSource: 0,
      dialogImageUrl: "",
      questionTypeList: [],
      fileUrl: this.$store.state.common.fileHost,
      dialogAnswerImgVisible: false,
      uploadAction: this.$httpApi.httpUrl("/api/upload/2"),
      currentQuestion: {}, // 当前试题
      currentQuestionPage: 0,
      questionAnswerDataList: [], // 试题答案列表
      questionTotal: 0
    };
  },

  beforeCreate() {
    thisPage = this;
  },

  mounted() {
    getDictValueByType("question_type").then(response => {
      this.questionTypeList = response.data.data;
    });
    this.questionAnswerDataList = this.questionInfoAnswerList;
    if (this.questionAnswerDataList.length > 0) {
      this.currentQuestion = this.questionAnswerDataList[0];
    }
  },

  methods: {
    changeQuestionIndex(index) {
      if (index === -1 && this.currentQuestionPage !== 0) {
        // 切换上一题
        this.currentQuestionPage -= 1;
      }

      if (index === 1 && this.currentQuestionPage < this.questionTotal) {
        this.currentQuestionPage += 1;
      } else if (this.currentQuestionPage === this.questionTotal - 1) {
        this.$message.error("亲, 已经是最后一题了");
      }
      this.parserQuestion();
      this.$emit("changeQuestionIndex", this.questionAnswerDataList);
    },

    parserQuestion() {
      this.currentQuestion = this.questionAnswerDataList[
        this.currentQuestionPage
      ];
      if (this.currentQuestion.options) {
        this.currentQuestion.optionList = JSON.parse(
          this.currentQuestion.options
        );
      }
    },

    // 答题卡切换试题
    selectQuestion(index) {
      this.currentQuestionPage = index - 1;
      this.parserQuestion();
      this.openAnswerFlag = false;
    },

    openWritingBoard() {
      location.href =
        this.$store.state.host + "/static/writingBoard/writingBoard.html";
      this.$emit("openWritingBoard");
      window.open("/static/writingBoard/writingBoard.html");
    },

    lookAnswerImg(url) {
      this.dialogAnswerImgVisible = true;
      this.dialogImageUrl = url;
    },

    deleteAnswerImg(url) {
      this.$confirm("确定移除该答案吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        let index = this.currentQuestion.studentAnswer.indexOf(url);
        this.currentQuestion.studentAnswer.splice(index, 1);
      });
    },

    // 提交
    commitQuestion() {
      let noAnswerCount = 0;

      let questionAnswerParam = [];
      this.questionAnswerDataList.forEach(item => {
        let studentAnswerStr = "";
        if (item.studentAnswer instanceof Array) {
          if (item.studentAnswer.length === 0) {
            noAnswerCount++;
          } else {
            item.studentAnswer.forEach(value => {
              studentAnswerStr += value + ",";
            });
            studentAnswerStr = studentAnswerStr.substr(
              0,
              studentAnswerStr.length - 1
            );
          }
        } else {
          if (!item.studentAnswer) {
            noAnswerCount++;
          } else {
            studentAnswerStr = item.studentAnswer;
          }
        }

        questionAnswerParam.push({
          questionInfoId: item.questionInfoId,
          answer: item.answer, // 试题答案
          questionType: item.questionType, // 试题类型
          questionMark: item.mark, // 试题得分
          studentAnswer: studentAnswerStr //学员试题答案
        });
      });

      if (this.timeOutFlag) {
        this.$emit("afterCommit", questionAnswerParam);
      } else {
        // 未答试题数量
        let message = "确定提交吗?";
        if (noAnswerCount > 0) {
          message = "您还有" + noAnswerCount + "题尚未做答, 确定提交吗?";
        }
        this.$confirm(message, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            this.$emit("afterCommit", questionAnswerParam);
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消提交"
            });
          });
      }
    },

    beforeUploadAnswerImg(file) {
      let isImage =
        file.type === "image/jpeg" ||
        file.type === "image/png" ||
        file.type === "image/gif";
      let isLimit = file.size / 1024 / 1024 < 1;

      if (!isImage) {
        this.$message.error("上传图片只能是 JPG/PNG 格式!");
      }
      if (!isLimit) {
        this.$message.error("上传图片大小不能超过 1MB!");
      }
      return isImage && isLimit;
    },

    uploadAnswerImgSuccess(res) {
      if (res.code === 1) {
        this.currentQuestion.studentAnswer.push(res.url);
        this.$message.success("答案上传成功");
      } else {
        this.$message.error(res.message);
      }
    },

    hasAnswerQuestion(val) {
      if (val instanceof Array) {
        return val.length > 0;
      }
      if (val) {
        return true;
      }
      return false;
    }
  },

  filters: {
    getQuestionTypeName(questionType) {
      for (let i = 0; i < thisPage.questionTypeList.length; i++) {
        if (thisPage.questionTypeList[i].code === questionType) {
          return thisPage.questionTypeList[i].value;
        }
      }
      return "无";
    }

    /* parserAnswer (val) {
          if (val instanceof Array) {
            let value = ''
            for (let item in val) {
              value += val[item] + ","
            }
            return value.substr(0, value.length - 1)
          }
          return  val
        }*/
  }
};
</script>

<style lang="scss">
.testbox p {
  display: inline-block;
}

/*.el-upload--picture-card {*/
/*  width: 100px !important;*/
/*  height: 90px !important;*/
/*  line-height: 100px !important;*/
/*  vertical-align: top;*/
/*}*/
.tops {
  display: inline-block;
  text-align: center;
  width: 100px;
  height: 40px;
  line-height: 40px;
  border-radius: 40px;
  background-color: #fad303;
  box-shadow: 3px 2px 0 0 rgba(250, 213, 3, 0.445);
}

.el-pagination {
  margin-top: 15px;
  text-align: center;
}

.title3 .count {
  position: relative;
  overflow: overlay;
  display: inline-block;
  font-weight: bolder;
  left: 86px;
  color: red;
  font-size: 45px;
}
.title3 .mark {
  width: 205px;
  top: 85px;
  left: 57%;
  height: 64px;
  position: absolute;
}
.el-radio-button__inner,
.el-radio-group {
  padding-top: 20px !important;
  display: block !important;
}

.testinfo {
  padding-left: 30px;
  padding-top: 22px;
}
.title3 {
  width: 100%;
  height: 30px;
  text-align: center;
}

.subbtnbox {
  width: 100%;
  text-align: center;
  padding: 20px;
  .btn {
    width: 100px;
    background-color: red;
  }
}
.testbox {
  display: inline-block;
  margin-top: 20px;
}

.btnbox {
  display: flex;
  display: -webkit-flex;
  justify-content: space-around;
  padding-left: 100px;
  margin-top: 30px;
  padding-right: 100px;
}
.play {
  display: inline-block;
  height: 30px;
  line-height: 30px;
  padding-left: 10px;
  color: red;
  cursor: pointer;
  .cp {
    font-size: 20px;
    vertical-align: middle;
  }
}
</style>
