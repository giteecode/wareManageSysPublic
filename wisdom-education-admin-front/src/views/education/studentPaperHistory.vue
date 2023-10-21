<template>
  <div>
    <el-table
      :data="studentTestPaperList"
      border
      v-loading="loading"
      style="width: 100%;">

      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>

      <el-table-column
        prop="name"
        align="center"
        label="试卷名称">
      </el-table-column>

      <el-table-column
        prop="subject_name"
        align="center"
        label="科目">
      </el-table-column>

      <el-table-column
        prop="do_paper_date"
        align="center"
        :formatter="formatterDoPaperDate"
        label="考试时间">
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        min-width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button
            @click="lookStudentPaperQuestion(scope.row)"
            size="small"
            type="warning">人工评卷</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      @size-change="sizeChangeHandler"
      @current-change="currentChangeHandler"
      :current-page="currentPage"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="pageSize"
      :total="totalCount"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>

<script>
  export default {
    name: 'studentPaperHistory',
    data () {
      return {
        studentTestPaperList: [],
        loading: true,
        modeId: '',
        studentId: '',
        currentPage: 1,
        pageSize: 10,
        totalCount: 0
      }
    },

    mounted () {
      this.modeId = this.$route.query.modeId
      this.studentId = this.$route.query.studentId
      this.getStudentTestPaperList()
    },

    methods: {

      getStudentTestPaperList () {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/studentInfo/getStudentPaperList'), {
          params: {
            sqlId: 'student.info.mode.testPaperList',
            offset: (that.currentPage - 1) * that.pageSize,
            limit: that.pageSize,
            studentId: that.studentId,
            modeId: that.modeId
          }
        }).then(function (response) {
          that.studentTestPaperList = response.data.data
          that.loading = false
          that.totalCount = response.data.total
        })
      },

      lookStudentPaperQuestion (item) {
        this.$router.push({
          name: 'studentQuestionHistory',
          query: {
            studentId: this.studentId,
            modeId: this.modeId,
            testPaperId: item.id
          }
        })
      },

      sizeChangeHandler (val) {
        this.pageSize = val
        this.currentPage = 1
        this.getStudentTestPaperList()
      },

      formatterDoPaperDate (row, column) {
        if (!row.create_date) {
          return '--'
        }
        return this.moment(row.create_date).format('YYYY-MM-DD HH:mm')
      },

      currentChangeHandler (val) {
        this.currentPage = val
        this.getStudentTestPaperList()
      }
    }
  }
</script>

<style scoped>

</style>
