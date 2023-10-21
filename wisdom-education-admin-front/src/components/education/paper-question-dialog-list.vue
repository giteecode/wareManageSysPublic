<template>
  <div>
    <el-dialog
      width="70%"
      @closed="closeQuestionDialog"
      title="试题管理"
      :visible.sync="dialogVisible">
      <el-form :inline="true">
        <el-form-item>
          <el-select
            @change="changeQuestionType"
            v-model="questionType"
            filterable
            clearable
            placeholder="试题类型">
            <el-option
              v-for="item in questionTypeList"
              :key="item.id"
              :label="item.value"
              :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            v-if="!queryParams.publishFlag"
            plain @click="addQuestion"
            icon="el-icon-plus">题库导入</el-button>
        </el-form-item>

        <!--<el-form-item>-->
          <!--<el-button type="primary" icon="el-icon-upload2">导出</el-button>-->
        <!--</el-form-item>-->
      </el-form>

      <vxe-table
        border="full"
        resizable
        @edit-actived="cellEditOpen"
        @edit-closed="cellEditClose"
        :data="questionListForPaper"
        :edit-config="{trigger: 'click', mode: 'cell'}">
        <vxe-table-column
          type="checkbox"
          width="60">
        </vxe-table-column>

        <vxe-table-column
          field="content"
          title="试题内容"
          min-width="150" >
          <template slot-scope="scope">
            <div v-html="scope.row.content"></div>
          </template>
        </vxe-table-column>

        <vxe-table-column
          align="center"
          title="试题类型"
          width="120">
          <template slot-scope="scope">
            <el-tag size="small" >{{scope.row.questionType | getQuestionTypeName}}</el-tag>
          </template>
        </vxe-table-column>

        <vxe-table-column
          field="mark"
          align="center"
          title="分数"
          :edit-render="{name: 'input', attrs: {type: 'text', disabled: queryParams.publishFlag}}">
        </vxe-table-column>

        <vxe-table-column
          align="center"
          field="sort"
          title="排序"
          :edit-render="{name: 'input', attrs: {type: 'text', disabled: queryParams.publishFlag}}">>
        </vxe-table-column>

        <vxe-table-column
          title="操作"
          v-if="!queryParams.publishFlag"
          align="center"
          width="150" >
          <template slot-scope="scope">
            <el-button
              size="small"
              @click="removePaperQuestion(scope.row)"
              type="danger">移除</el-button>
          </template>
        </vxe-table-column>
      </vxe-table>

      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        :total="totalCount"
        layout="total, prev, pager, next, jumper">
      </el-pagination>
    </el-dialog>

    <!-- 所有试题列表 -->
    <el-dialog width="70%"   title="试题导入" :visible.sync="questionDialog">
      <el-form :inline="true">
        <el-form-item>
          <el-select
            @change="changeQuestionTypeByList"
            v-model="allQuestionQuery.questionType"
            filterable
            clearable
            placeholder="试题类型">
            <el-option
              v-for="item in questionTypeList"
              :key="item.id"
              :label="item.value"
              :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-input v-model="allQuestionQuery.content" placeholder="输入题目内容" type="text"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="success" @click="searchQuestion" icon="el-icon-search">搜索</el-button>
        </el-form-item>
      </el-form>

     <el-table border @selection-change="questionHandleSelectionChange" :data="questionList">
        <el-table-column
          type="selection"
          align="center"
          width="50">
        </el-table-column>

       <el-table-column
         align="center"
         label="试题类型"
         width="200">
         <template slot-scope="scope">
           <el-tag size="small" >{{scope.row.questionType | getQuestionTypeName}}</el-tag>
         </template>
       </el-table-column>

        <el-table-column
          prop="content"
          align="center"
          :show-overflow-tooltip="true"
          label="试题内容"
          min-width="150">
          <template slot-scope="scope">
            <div v-html="scope.row.content"></div>
          </template>
        </el-table-column>

       <el-table-column
         align="center"
         :show-overflow-tooltip="true"
         label="操作"
         width="150">
         <template slot-scope="scope">
           <el-button
             size="small"
             @click="lookQuestion(scope.row)"
             >预览</el-button>
         </template>
       </el-table-column>
      </el-table>

      <el-pagination
        background
        @size-change="questionHandleSize"
        @current-change="questionHandleCurrentPage"
        :current-page="questionCurrentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="questionPageSize"
        :total="questionTotalCount"
        layout="total, prev, pager, next, jumper">
      </el-pagination>

      <div slot="footer" class="dialog-footer">
        <el-button @click="questionDialog = false">取 消</el-button>
        <el-button @click="saveRelevanceQuestion" type="primary">确 定</el-button>
      </div>
    </el-dialog>

    <question-preview
      @closeQuestionPreview="closeQuestionPreview"
      :questionInfo="rowQuestion"
      :openFlag="questionPreviewFlag">
    </question-preview>
  </div>
</template>

<script>
  import questionPreview from '@/components/education/question-preview'
  import {getDictValueByType} from '@/api/dict'
  import {isInt} from '../../utils/validate'
  let thisPage = null
  export default {
    name: 'paper-question-dialog-list',
    components: {questionPreview},
    props: {
      showFlag: {
        type: Boolean,
        required: false,
        default: false
      },
      gradeTypeList: {
        type: Array,
        required: false,
        default () {
          return []
        }
      },

      params: {
        type: Object,
        required: true,
        default: {}
      }
    },

    watch: {
      params (val) {
        this.queryParams = val
        this.getQuestionListForPaper()
      },

      showFlag (val) {
        this.dialogVisible = val
      }
    },

    beforeCreate () {
      thisPage = this
    },

    mounted () {
      this.queryParams = this.params
      this.dialogVisible = this.showFlag
      getDictValueByType('question_type').then(response => {
        this.questionTypeList = response.data.data
      })
    },

    data () {
      return {
        rowQuestion: null,
        questionPreviewFlag: false,
        allQuestionQuery: {
          content: '',
          questionType: ''
        },
        questionType: '',
        queryLanguagePointsArrayId: [],
        questionDialog: false,
        questionTypeList: [],
        questionList: [],
        questionListForPaper: [],
        queryParams: {},
        dialogVisible: false,
        currentPage: 1,
        questionCurrentPage: 1,
        pageSize: 10,
        rowIndexData: {
          sort: null,
          mark: null
        },
        questionPageSize: 10,
        totalCount: 0,
        checkQuestionArray: [],
        checkDeleteQuestionIds: [],
        questionTotalCount: 0
      }
    },

    methods: {

      // 试题预览
      lookQuestion (item) {
        this.questionPreviewFlag = true
        this.rowQuestion = item
      },

      closeQuestionPreview () {
        this.questionPreviewFlag = false
      },

      cellEditOpen (row) {

      },

      cellEditClose (row) {
        let data = {
          id: this.questionListForPaper[row.rowIndex].id,
          testPaperInfoId: this.queryParams.id,
          questionInfoId: this.questionListForPaper[row.rowIndex].questionInfoId,
          sort: this.questionListForPaper[row.rowIndex].sort,
          mark: this.questionListForPaper[row.rowIndex].mark
        }

        if (!isInt(data.sort)) {
          this.$message.error('排序值必须为正数')
          return false
        }

        if (!isInt(data.mark)) {
          this.$message.error('试题分数必须为正数')
          return false
        }

        this.axios.put(this.$http.httpUrl('/system/testPaperInfo/markOrSort'), data)
      },

      closeQuestionDialog () {
        this.dialogVisible = false
        this.$emit('closeDialog')
      },

      removePaperQuestion (item) {
        let that = this
        let params = {
          id: item.id,
          testPaperInfoId: item.testPaperInfoId,
          questionInfoId: item.questionInfoId,
          mark: item.mark
        }
        that.$confirm('确定移除选中的试题吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          that.axios.delete(that.$http.httpUrl('/system/testPaperInfo/question'), {
            data: params
          }).then(function (response) {
            if (response.data.code === 1) {
              that.$message({
                type: 'success',
                message: response.data.message
              })
              that.questionListForPaper.splice(that.questionListForPaper.findIndex(value => item.id === value.id), 1)
              that.totalCount -= 1
            }
          })
        })
      },

      questionHandleSelectionChange (val) {
        this.checkQuestionArray = val
      },

      addQuestion () {
        // this.questionType = ''
        this.allQuestionQuery = {
          content: '',
          questionType: ''
        }
        this.getQuestionList()
        this.questionDialog = true
      },

      questionHandleSize (val) {
        this.questionCurrentPage = 1
        this.questionPageSize = val
        this.getQuestionList()
      },

      questionHandleCurrentPage (val) {
        this.questionCurrentPage = val
        this.getQuestionList()
      },

      // 获取试卷关联试题列表
      getQuestionListForPaper () {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/testPaperInfo/question'), {
          params: {
            testPaperInfoId: that.queryParams.id,
            pageNumber: that.currentPage,
            pageSize: that.pageSize,
            questionType: that.questionType
          }
        }).then(function (response) {
          that.questionListForPaper = response.data.data.dataList
          that.totalCount = response.data.data.total
        }).catch(function (error) {
          console.log(error)
        })
      },

      searchQuestion () {
        this.questionCurrentPage = 1
        this.getQuestionList()
      },

      getQuestionList () {
        let that = this
        let selectQuestionIds = []
        this.questionListForPaper.forEach(question => {
          selectQuestionIds.push(question.questionInfoId)
        })
        that.axios.get(that.$http.httpUrl('/system/question'), {
          params: {
            questionIds: selectQuestionIds + '',
            pageNumber: that.questionCurrentPage,
            pageSize: that.questionPageSize,
            questionType: that.allQuestionQuery.questionType,
            content: that.allQuestionQuery.content,
            schoolType: that.queryParams.schoolType,
            gradeInfoId: that.queryParams.gradeInfoId,
            subjectId: that.queryParams.subjectId
          }
        }).then(function (response) {
          that.questionList = response.data.data.dataList
          that.questionTotalCount = response.data.data.total
        }).catch(function (error) {
          console.log(error)
        })
      },

      handleSizeChange (val) {
        this.pageSize = val
        this.currentPage = 1
        this.getQuestionListForPaper()
      },

      handleCurrentChange (val) {
        this.currentPage = val
        this.getQuestionListForPaper()
      },

      changeQuestionType (val) {
        this.questionType = val
        this.getQuestionListForPaper()
      },

      changeQuestionTypeByList (val) {
       // this.questionType = val
        // this.getQuestionList()
      },

      /**
       * 保存关联试题
       */
      saveRelevanceQuestion () {
        if (this.checkQuestionArray.length === 0) {
          this.$message.error('请选择试题')
        } else {
          let params = []
          this.checkQuestionArray.forEach(question => {
            params.push({
              testPaperInfoId: this.queryParams.id,
              questionInfoId: question.id
            })
          })

          this.axios.post(this.$http.httpUrl('/system/testPaperInfo/question'), params)
            .then(response => {
              this.questionDialog = false
              this.$message.success(response.data.message)
              this.getQuestionListForPaper()
            })
        }
      }
    },
    filters: {

      getQuestionTypeName (questionType) {
        for (let i = 0; i < thisPage.questionTypeList.length; i++) {
          if (thisPage.questionTypeList[i].code === questionType) {
            return thisPage.questionTypeList[i].value
          }
        }
      }
    }
  }
</script>

<style>
</style>
