<template>
  <el-container>
    <el-header>
      <div class="title_item">
        期末考试测试
      </div>
      <div class="header_right">
        <div class="title_item_right">
          及格分: 60分/100分
        </div>
        <div class="title_item_right">
          倒计时：00时05分58秒
        </div>
        <div class="title_item_right">
          <el-button
            @click="commitQuestion"
            style="margin-top: 0px"
            type="primary"
            >立即交卷</el-button
          >
        </div>
      </div>
    </el-header>
    <el-container>
      <el-aside width="240px">
        <div class="question_num">
          <div class="question_num_list">
            <template v-for="(question, index) in testPaperInfoQuestionList">
              <el-button
                v-if="hasAnswerQuestion(question.studentAnswer)"
                type="primary"
                :key="++index"
                @click="selectQuestion(index)"
                size="medium"
                circle
                >{{ index }}</el-button
              >
              <el-button
                v-else
                :key="++index"
                @click="selectQuestion(index)"
                size="medium"
                circle
                >{{ index }}</el-button
              >
            </template>
          </div>
        </div>
      </el-aside>
      <el-main>
        <div class="main_content">
          <div class="title">
            <h3>
              {{ currentQuestion.questionType | getQuestionTypeName }} ({{
                currentQuestion.mark
              }}分)
            </h3>
          </div>

          <div class="question-content">
            <div class="item">
              <label>{{ currentQuestionPage + 1 }}.</label>
              <div
                style="display: inline-block"
                v-html="currentQuestion.content"
              ></div>
            </div>

            <div class="item">
              <el-row
                class="test-opt"
                style="display: grid; padding-left: 20px;margin-top: 15px"
              >
                <template
                  style="margin-top: 15px"
                  v-if="currentQuestion.questionType === 1"
                >
                  <el-radio-group
                    v-for="(item, index) in currentQuestion.optionList"
                    :key="index"
                    v-model="currentQuestion.studentAnswer"
                  >
                    <el-radio style="display:inline-flex;" :label="item.label"
                      >{{ item.label }}. &nbsp;
                      <label v-html="item.option_name"></label>
                    </el-radio>
                  </el-radio-group>
                </template>

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

                <template v-if="currentQuestion.questionType === 6">
                  <el-radio v-model="currentQuestion.studentAnswer" label="1"
                    >对</el-radio
                  >
                  <el-radio v-model="currentQuestion.studentAnswer" label="0"
                    >错</el-radio
                  >
                </template>
              </el-row>
            </div>
          </div>

          <div class="btn">
            <el-button type="warning" @click="changeQuestionIndex(-1)" round>
              <i class="el-icon-arrow-left"></i>
              上一题
            </el-button>

            <el-button
              v-if="
                questionTotal > 1 && currentQuestionPage !== questionTotal - 1
              "
              type="warning"
              @click="changeQuestionIndex(1)"
              round
              >下一题 <i class="el-icon-arrow-right"></i
            ></el-button>
          </div>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>
