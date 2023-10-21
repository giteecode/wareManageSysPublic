<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="keyWord" placeholder="学员姓名/模式名称/题目内容" style="width: 100%"></el-input>
      </el-form-item>

      <el-form-item>
        <el-select clearable filterable v-model="query.grade_type" placeholder="请选择年级">
          <el-option
            v-for="item in gradeTypeList"
            :key="item.id"
            :label="item.value"
            :value="item.code">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-select
          v-if="query.grade_type"
          clearable filterable
          v-model="query.subjectId"
          placeholder="请选择科目">
          <el-option
            v-for="item in changeSubjectList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary" @click="searchQuestionHistory" icon="el-icon-search">查询</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="questionHistoryList"
      border
      v-loading="loading"
      style="width: 100%;">

      <el-table-column
        type="selection"
        align="center"
        width="50">
      </el-table-column>

      <el-table-column
        prop="student_name"
        align="center"
        label="学员姓名">
      </el-table-column>

      <el-table-column
        prop="content"
        align="center"
        min-width="200"
        label="试题内容">
        <template slot-scope="scope">
          <div v-html="scope.row.content"></div>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        label="试题类型">
        <template slot-scope="scope">
          <el-tag size="small" >{{scope.row.question_type | getQuestionTypeName}}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="mode_name"
        align="center"
        label="模式名称">
      </el-table-column>

      <el-table-column
        prop="subject_name"
        align="center"
        label="科目">
      </el-table-column>

      <el-table-column
        prop="user_answer"
        align="center"
        label="用户答案">
        <template slot-scope="scope">
          <template v-if="scope.row.user_answer">
            <template v-if="scope.row.question_type === 6">
              <el-tag v-if="scope.row.user_answer === 1" type="success">√</el-tag>
              <el-tag v-else type="danger">×</el-tag>
            </template>
            <template v-else>
              <div>{{scope.row.user_answer}}</div>
            </template>
          </template>

          <template v-else>
            <el-tag type="danger">未作答</el-tag>
          </template>
        </template>
      </el-table-column>

      <el-table-column
        prop="answer"
        align="center"
        label="正确答案">
        <template slot-scope="scope">
            <template v-if="scope.row.question_type === 6">
              <el-tag v-if="scope.row.answer === 1" type="success">√</el-tag>
              <el-tag v-else type="success">×</el-tag>
            </template>
            <template v-else>
              <div v-html="scope.row.answer"></div>
            </template>
        </template>
      </el-table-column>

      <el-table-column
        prop="is_right"
        align="center"
        label="是否正确">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.is_right === 1" type="success">正确</el-tag>
          <el-tag v-else type="danger">错误</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="create_date"
        align="center"
        width="180"
        :formatter="formatterCreateDate"
        label="答题时间">
      </el-table-column>

      <el-table-column
        align="center"
        min-width="100"
        label="操作">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.question_type != 3 && scope.row.question_type != 4 && scope.row.question_type != 6"
            size="small"
            type="warning">批阅</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      @size-change="changeSize"
      @current-change="changePage"
      :current-page="currentPage"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="pageSize"
      :total="totalCount"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>
<script>
  import { getSubjectList, getQuestionTypeApi, getGradeType } from '@/utils/education-api'
  let thisPage = null
  export default {
    name: 'questionHistory',
    data () {
      return {
        keyWord: '',
        questionHistoryList: [],
        currentPage: 1,
        pageSize: 10,
        loading: true,
        totalCount: 0,
        subjectList: [],
        gradeTypeList: [],
        questionType: [],
        changeSubjectList: [],
        query: {
          grade_type: '',
          subjectId: ''
        }
      }
    },

    watch: {

      'query.grade_type' (val) {
        this.getSubjectListByGradeType(val)
      }
    },

    beforeCreate () {
      thisPage = this
    },

    mounted () {
      this.getUserAnswerQuestionList()
      getSubjectList(function (data) {
        thisPage.subjectList = data
      })

      getQuestionTypeApi(function (data) {
        thisPage.questionType = data
      })

      getGradeType(function (data) {
        thisPage.gradeTypeList = data
      })
    },

    methods: {

      getSubjectListByGradeType (val) {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/subject'), {
          params: {
            sqlId: 'system.subject.list',
            gradeType: val
          }
        }).then(function (response) {
          that.changeSubjectList = response.data.data
        }).catch(function (error) {
          console.log(error)
        })
      },

      getUserAnswerQuestionList () {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/mode/getUserAnswerQuestionList'), {
          params: {
            sqlId: 'user.question.answer.list',
            offset: (that.currentPage - 1) * that.pageSize,
            limit: that.pageSize,
            keyWord: that.keyWord,
            gradeType: that.query.grade_type,
            subjectId: that.query.subjectId
          }
        }).then(function (response) {
          that.questionHistoryList = response.data.data
          that.totalCount = response.data.total
          that.loading = false
        }).catch(function (error) {
          console.log(error)
        })
      },

      formatterCreateDate (row, column) {
        if (!row.create_date) {
          return '--'
        }
        return this.moment(row.create_date).format('YYYY-MM-DD HH:mm')
      },

      searchQuestionHistory () {
        this.currentPage = 1
        this.getUserAnswerQuestionList()
      },

      changeSize (val) {
        this.pageSize = val
        this.currentPage = 1
        this.getUserAnswerQuestionList()
      },

      changePage (val) {
        this.currentPage = val
        this.getUserAnswerQuestionList()
      }
    },

    filters: {

      getQuestionTypeName (questionType) {
        for (let i = 0; i < thisPage.questionType.length; i++) {
          if (thisPage.questionType[i].code === questionType) {
            return thisPage.questionType[i].value
          }
        }
      }
    }
  }
</script>


<style scoped>

</style>
