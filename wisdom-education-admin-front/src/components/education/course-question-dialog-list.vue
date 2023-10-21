<template>
  <div>
    <el-dialog
      width="70%"
      @closed="closeCourseQuestionDialog"
      title="试题管理"
      :visible.sync="courseQuestionDialogVisible">
      <el-form :inline="true">
        <el-form-item>
          <el-select
            @change="changeQuestionType"
            v-model="queryParams.question_type"
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
          <el-button type="primary" @click="searchCourseQuestion" icon="el-icon-search">查询</el-button>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" plain @click="addQuestion" icon="el-icon-plus">题库导入</el-button>
        </el-form-item>

      </el-form>
      <el-table style="width: 100%" @cell-click="cellClick" cell-mouse-leave="cellMouseLeave" :data="courseQuestionList">
        <el-table-column
          prop="content"
          :show-overflow-tooltip="true"
          label="试题内容"
          min-width="250">
          <template slot-scope="scope">
            <div v-html="scope.row.content"></div>
          </template>
        </el-table-column>

        <el-table-column
          prop="question_type"
          label="试题类型"
          width="100">
          <template slot-scope="scope">
            <el-tag size="small" >{{scope.row.question_type | getQuestionTypeName}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          prop="subject_name"
          label="科目"
          width="100">
        </el-table-column>


        <el-table-column
          align="center"
          prop="mark"
          label="分数"
          width="100">
          <template slot-scope="scope">
            <el-input v-if="scope.row.showMarkInput" size="small" @blur="changeMark(scope.row)" v-model="scope.row.mark"/>
            <template v-else>{{scope.row.mark}}</template>
          </template>
        </el-table-column>

        <el-table-column
          align="sort"
          label="排序"
          width="100">
          <template slot-scope="scope">
            <el-input v-if="scope.row.showSortInput" size="small" @blur="changeSort(scope.row)" v-model="scope.row.sort"/>
            <template v-else>{{scope.row.sort}}</template>
          </template>
        </el-table-column>

        <el-table-column
          prop="school_type"
          width="120"
          label="阶段">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.school_type === 1" size="small">小学</el-tag>
            <el-tag v-else-if="scope.row.school_type === 2" size="small">初中</el-tag>
            <el-tag v-else-if="scope.row.school_type === 3" size="small">高中</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          prop="grade_type"
          width="120"
          label="年级">
          <template slot-scope="scope">
            <el-tag size="small">{{scope.row.grade_type | getGradeName}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          align="center"
          width="120"
          label="操作">
          <template slot-scope="scope">
            <el-button
              size="small"
              @click="deleteCourseQuestion(scope.row)"
              type="danger">移除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
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

          <el-form-item>
            <el-button type="primary" @click="searchAllQuestion" icon="el-icon-search">查询</el-button>
          </el-form-item>
        </el-form-item>
      </el-form>

      <el-table @selection-change="questionHandleSelectionChange" :data="questionList">
        <el-table-column
          type="selection"
          align="center"
          width="50">
        </el-table-column>

        <el-table-column
          prop="content"
          :show-overflow-tooltip="true"
          label="内容"
          min-width="150">
          <template slot-scope="scope">
            <div v-html="scope.row.content"></div>
          </template>
        </el-table-column>

        <el-table-column
          prop="question_type"
          label="试题类型"
          width="100">
          <template slot-scope="scope">
            <el-tag size="small" >{{scope.row.question_type | getQuestionTypeName}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          prop="subject_name"
          label="科目"
          width="120">
        </el-table-column>

        <el-table-column
          prop="school_type"
          width="120"
          label="阶段">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.school_type === 1" size="small">小学</el-tag>
            <el-tag v-else-if="scope.row.school_type === 2" size="small">初中</el-tag>
            <el-tag v-else-if="scope.row.school_type === 3" size="small">高中</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          prop="grade_type"
          width="120"
          label="年级">
          <template slot-scope="scope">
            <el-tag size="small">{{scope.row.grade_type | getGradeName}}</el-tag>
          </template>
        </el-table-column>

      </el-table>

      <el-pagination
        background
        @size-change="questionHandleSizeChange"
        @current-change="questionHandleCurrentPage"
        :current-page="questionCurrentPage"
        :page-size="questionPageSize"
        :total="questionTotalCount"
        layout="total, prev, pager, next, jumper">
      </el-pagination>

      <div slot="footer" class="dialog-footer">
        <el-button @click="questionDialog = false">取 消</el-button>
        <el-button @click="saveRelevanceQuestion" type="primary">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {getQuestionTypeListApi} from '@/utils/system-dict-api'
  let thisPage = null
  export default {
    name: 'course-question-dialog-list',
    props: {
      params: {
        type: Object,
        required: true,
        default: {}
      },

      gradeTypeList: {
        type: Array,
        required: true,
        default () {
          return []
        }
      },

      showFlag: {
        type: Boolean,
        required: false,
        default: false
      }
    },

    watch: {
      params (val) {
        this.queryParams = val
        this.getCourseQuestionList()
      },

      showFlag (val) {
        this.courseQuestionDialogVisible = val
      },

      'courseQuestionList' (val) {
        this.courseQuestionList = val
      }
    },

    beforeCreate () {
      thisPage = this
    },

    mounted () {
      this.getQuestionType()
    },

    data () {
      return {
        courseQuestionIds: [], // 存储课程试题id集合
        courseQuestionDialogVisible: false,
        treeLanguagePointsList: [],
        languagePointsArrayId: [],
        languagePointsArrayIdForQuestion: [],
        courseQuestionList: [],
        questionTypeList: [],
        pageSize: 10,
        currentPage: 1,
        totalCount: 0,
        checkQuestionArray: [],
        questionTotalCount: 0,
        questionPageSize: 10,
        questionCurrentPage: 1,
        questionList: [],
        queryParams: {},
        questionType: '',
        questionDialog: false,
        courseQuestionType: ''
      }
    },

    methods: {

      cellClick (row, column, cell, event) {
        let questionArray = []
        this.courseQuestionList.forEach(item => {
          questionArray.push(item)
        })
        if (column.label === '分数' || column.label === '排序') {
          let rowId = row.id
          for (let i in questionArray) {
            let item = questionArray[i]
            if (questionArray[i].id === rowId) {
              if (column.label === '排序') {
                item.showSortInput = true
              } else {
                item.showMarkInput = true
              }
              break
            }
          }
          this.courseQuestionList = questionArray
        }
      },

      changeQuestionType (val) {
        this.queryParams.question_type = val
        this.getCourseQuestionList()
      },

      searchCourseQuestion () {
        this.currentPage = 1
        this.getCourseQuestionList()
      },

      changeSort (item) {
        let that = this
        item.questionInfoId = item.id
        item.updateType = 0
        that.axios.post(that.$http.httpUrl('/system/course/updateCourseQuestionSortOrMark'), item)
          .then(function (response) {
          if (response.data.code === 1) {
            that.$message.success('操作成功')
          } else {
            that.$message.error('操作异常')
          }
        })
      },

      closeCourseQuestionDialog () {
        this.courseQuestionDialogVisible = false
        this.$emit('closeCourseQuestionDialog')
      },

      questionHandleSelectionChange (val) {
        this.checkQuestionArray = val
      },

      changeMark (item) {
        let that = this
        item.questionInfoId = item.id
        let questionArray = []
        this.courseQuestionList.forEach(item => {
          questionArray.push(item)
        })
        questionArray.forEach(question => {
          if (question.id === item.id) {
            item.showMarkInput = false
          }
        })
        this.courseQuestionList = questionArray
        item.updateType = 1
        that.axios.post(that.$http.httpUrl('/system/course/updateCourseQuestionSortOrMark'), item)
          .then(function (response) {
          if (response.data.code === 1) {
            that.$message.success(response.data.message)
          } else {
            that.$message.error(response.data.message)
          }
        })
      },

      getCourseQuestionList () {
        let that = this
        that.courseQuestionList = []
        that.axios.get(that.$http.httpUrl('/system/course/getCourseQuestionList'), {
          params: {
            courseId: that.queryParams.id,
            questionType: that.queryParams.question_type,
            subjectId: that.queryParams.subject_id,
            currentPage: that.currentPage,
            offset: (that.currentPage - 1) * that.pageSize,
            limit: that.pageSize
          }
        }).then(function (response) {
          that.courseQuestionList = response.data.data.dataList
          that.courseQuestionIds = response.data.data.courseQuestionIds
          that.courseQuestionList.forEach(courseQuestion => {
            courseQuestion.showMarkInput = false
            courseQuestion.showSortInput = false
          })
          that.totalCount = response.data.data.total
        }).catch(function (error) {
          console.log(error)
        })
      },

      handleSizeChange (val) {
        this.pageSize = val
        this.currentPage = 1
        this.getCourseQuestionList()
      },

      questionHandleCurrentPage (val) {
        this.questionCurrentPage = val
        this.getQuestionList()
      },

      questionHandleSizeChange (val) {
        this.questionPageSize = val
        this.getQuestionList()
      },

      changeQuestionTypeByList (val) {
        this.questionType = val
      },

      // 试题查询
      searchAllQuestion () {
        this.questionCurrentPage = 1
        this.getQuestionList()
      },

      addQuestion () {
        this.getQuestionList()
        this.questionDialog = true
      },

      getQuestionList () {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/question'), {
          params: {
            currentPage: that.questionCurrentPage,
            questionIds: that.courseQuestionIds,
            offset: (that.questionCurrentPage - 1) * that.questionPageSize,
            limit: that.questionPageSize,
            questionType: that.questionType,
            schoolType: that.queryParams.school_type,
            gradeType: that.queryParams.grade_type,
            subjectId: that.queryParams.subject_id
          }
        }).then(function (response) {
          that.questionList = response.data.data.dataList
          that.questionTotalCount = response.data.data.total
        }).catch(function (error) {
          console.log(error)
        })
      },

      getQuestionType () {
        let that = this
        getQuestionTypeListApi(function (data) {
          that.questionTypeList = data
        })
      },

      handleCurrentChange (val) {
        this.currentPage = val
        this.getCourseQuestionList()
      },

      deleteCourseQuestion (item) {
        let that = this
        that.$confirm('确定移除选中的试题吗', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let params = {
            courseId: that.queryParams.id,
            questionId: item.id
          }
          that.axios.delete(that.$http.httpUrl('/system/course//deleteCourseQuestion'), {
            data: params
          })
            .then(function (response) {
              if (response.data.code === 1) {
                that.$message.success(response.data.message)
                that.getCourseQuestionList()
              } else {
                that.$message.error(response.data.message)
              }
            }).catch(function (error) {
              console.log(error)
            })
        }).catch(() => {

        })
      },

      saveRelevanceQuestion () {
        let that = this
        if (that.checkQuestionArray.length === 0) {
          that.$message.error('请选择试题')
        } else {
          that.saveCourseQuestion(0, that)
        }
      },

      saveCourseQuestion (type, that) {
        let marks = []
        let checkQuestionIds = []
        that.checkQuestionArray.forEach(function (question) {
          checkQuestionIds.push(question.id)
          marks.push(question.mark)
        })
        let params = {
          courseId: that.queryParams.id,
          questionIds: checkQuestionIds,
          type: type,
          marks: marks
        }

        that.axios.post(that.$http.httpUrl('/system/course/relevanceQuestion'), params)
          .then(function (response) {
            if (response.data.code === 1) {
              that.questionDialog = false
              that.$message.success(response.data.message)
              that.getCourseQuestionList()
            } else {
              that.$message.error(response.data.message)
            }
          }).catch(function (error) {
            console.log(error)
          })
      }
    },

    filters: {
      getGradeName (gradeType) {
        for (let i = 0; i < thisPage.gradeTypeList.length; i++) {
          if (thisPage.gradeTypeList[i].code === gradeType) {
            return thisPage.gradeTypeList[i].value
          }
        }
      },

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
