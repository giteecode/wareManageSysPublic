<template>
  <div class="mod-role">
    <el-form :form="query" :inline="true" @keyup.enter.native="getLogs()">
      <el-form-item>
        <el-date-picker
          v-model="time"
          value-format="yyyy-MM-dd"
          type="daterange"
          @change="handelPotentialRangeChange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button v-if="hasPermission(['system:log:list'])" type="primary" icon="el-icon-search" @click="searchLogs">查询</el-button>
      </el-form-item>

<!--      <el-form-item>
        <el-button type="danger" icon="el-icon-delete" @click="clearLogs">清空</el-button>
      </el-form-item>-->
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="loading"
      style="width: 100%">
     <!-- <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>-->

      <el-table-column
        prop="operationName"
        align="center"
        label="操作人">
      </el-table-column>

      <el-table-column
        prop="requestUrl"
        align="center"
        label="请求Url">
      </el-table-column>

      <el-table-column
        prop="method"
        align="center"
        label="请求方式">
      </el-table-column>

      <el-table-column
        prop="requestTime"
        align="center"
        label="请求时间">
      </el-table-column>

      <el-table-column
        prop="operationDesc"
        align="center"
        label="操作详情" >
      </el-table-column>

      <el-table-column
        align="center"
        label="平台类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.platform_type === 1" size="small">后台系统</el-tag>
          <el-tag v-if="scope.row.platform_type === 2" size="small">学生端</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        prop="ip"
        label="ip地址" >
      </el-table-column>

      <el-table-column
        prop="createDate"
        align="center"
        label="操作时间">
      </el-table-column>

     <!-- <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button
            type="danger"
            size="small"
            v-if="hasPermission(['system:log:deleteById'])"
            @click="deleteById(scope.row)">删除</el-button>
        </template>
      </el-table-column>-->
    </el-table>

    <el-pagination
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
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
    data () {
      return {
        dataList: [],
        loading: true,
        time: '',
        query: {
          startTime: '',
          endTime: ''
        },
        currentPage: 1,
        pageSize: 10,
        totalCount: 0
      }
    },

    mounted () {
      this.getLogs()
    },

    methods: {

      clearLogs () {
        this.$confirm('确定清空所有操作日志吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.axios.delete(this.$http.httpUrl('/system/log'))
            .then(response => {
              this.$message.success(response.data.message)
              this.getLogs()
            })
        })
      },

      searchLogs () {
        this.currentPage = 1
        this.loading = true
        this.getLogs()
      },

      handleSizeChange (val) {
        this.pageSize = val
        this.currentPage = 1
        this.loading = true
        this.getLogs()
      },

      handleCurrentChange (val) {
        this.currentPage = val
        this.loading = true
        this.getLogs()
      },

      handelPotentialRangeChange (val) {
        if (val && val instanceof Array && val.length === 2) {
          this.query.startTime = val[0]
          this.query.endTime = val[1]
        }
      },
      deleteById (item) {
        item.sqlId = 'system.common.deleteById'
        item.table = 'system_log'
        let that = this
        that.$confirm('确定删除选中的日志?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          that.axios.delete(that.$http.httpUrl('/system/log'), {
            data: item
          }).then(function (response) {
            if (response.data.code === 1) {
              that.$message({
                type: 'success',
                message: response.data.message
              })
              that.query = {
                startTime: '',
                endTime: ''
              }
              that.getLogs()
            } else {
              that.$message({
                type: 'error',
                message: response.data.message
              })
            }
          })
        })
      },

      getLogs () {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/log'), {
          params: {
            pageNumber: that.currentPage,
            pageSize: that.pageSize,
            startTime: that.query.startTime,
            endTime: that.query.endTime
          }
        }).then(function (response) {
          that.loading = false
          that.dataList = response.data.data.dataList
          that.totalCount = response.data.data.total
        })
      }
    }
  }
</script>
