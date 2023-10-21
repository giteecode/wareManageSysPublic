<template>
    <div>
      <el-form :inline="true">
        <el-form-item>
          <el-input v-model="query.keyWord" placeholder="考试名称" clearable></el-input>
        </el-form-item>

        <el-form-item>
          <el-select clearable filterable v-model="query.gradeInfoId" placeholder="请选择年级">
            <el-option
              v-for="item in gradeInfoList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-select
             clearable filterable
             v-model="query.subjectId"
             placeholder="请选择科目">
            <el-option
              v-for="item in subjectList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button
            v-if="hasPermission(['system:exam:list'])"
            @click="searchExamInfo"
            icon="el-icon-search" type="primary">查询</el-button>
        </el-form-item>
      </el-form>

      <el-table
        :data="examInfoList"
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
          min-width="200"
          label="考试名称">
        </el-table-column>

        <el-table-column
          align="center"
          label="所属年级">
          <template slot-scope="scope">
            <el-tag size="small">{{scope.row.gradeName}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          align="center"
          label="考试科目">
          <template slot-scope="scope">
            <el-tag size="small">{{scope.row.subjectName}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          prop="examNumber"
          align="center"
          label="参考人数">
        </el-table-column>

        <el-table-column
          prop="avgSource"
          align="center"
          label="平均分">
        </el-table-column>

        <el-table-column
          prop="mark"
          align="center"
          label="总分">
        </el-table-column>

        <el-table-column
          header-align="center"
          align="center"
          min-width="210"
          label="操作">
          <template slot-scope="scope">
            <el-button
              size="small"
              type="warning">详情</el-button>
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
  import {getGradeInfoList} from '@/api/gradeInfo'
  import {getSubjectInfoList} from '@/api/subjectInfo'
  export default {
    name: 'exam',
    data () {
      return {
        query: {
          gradeInfoId: '',
          keyWord: '',
          subjectId: ''
        },
        subjectList: [],
        examInfoList: [],
        loading: true,
        gradeInfoList: [],
        currentPage: 1,
        pageSize: 10,
        totalCount: 0
      }
    },

    watch: {
      'query.gradeInfoId' (val) {
        let params = {
          gradeInfoId: val
        }
        getSubjectInfoList(params).then(response => {
          this.subjectList = response.data.data.dataList
        })
      }
    },

    mounted () {
      this.getCountExamList()

      getGradeInfoList({}).then(response => {
        this.gradeInfoList = response.data.data.dataList
      })
    },

    methods: {

      searchExamInfo () {
        this.currentPage = 1
        this.getCountExamList()
      },

      changeSize (val) {
        this.pageSize = val
        this.currentPage = 1
        this.getCountExamList()
      },

      changePage (val) {
        this.currentPage = val
        this.loading = true
        this.getCountExamList()
      },

      getCountExamList () {
        this.axios.get(this.$http.httpUrl('/system/exam/countExam'), {
          params: {
            pageNumber: this.currentPage,
            pageSize: this.pageSize
          }
        }).then(response => {
          this.examInfoList = response.data.data.dataList
          this.totalCount = response.data.data.total
          this.loading = false
        })
      }
    },

    filters: {

    }
  }
</script>

<style scoped>

</style>
