<template>
  <div class="mod-user">
    <el-table
      :data="onlineExamStudentList"
      border
      v-loading="loading"
      style="width: 100%;">

      <el-table-column
        header-align="center"
        align="center"
        width="150"
        prop="name"
        label="姓名">
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        width="200"
        prop="rateStr"
        label="答题进度">
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        min-width="150"
        prop="startExamTime"
        label="进入考试时间">
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        min-width="150"
        prop="ipAddress"
        label="考试地点">
      </el-table-column>

      <el-table-column
        prop="device"
        header-align="center"
        align="center"
        width="150"
        label="设备">
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        min-width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button
            @click="$message.error('功能暂未开放，尽请期待')"
            size="small"
            icon="el-icon-bottom">强制交卷</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageNumber"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="pageSize"
      :total="total"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'onLineUser',
  data () {
    return {
      onlineExamStudentList: [],
      total: 0,
      pageNumber: 1,
      pageSize: 10,
      loading: true,
      dataTimer: null
    }
  },

  computed: {
    ...mapGetters({
      testPaperInfoId: 'map/getTestPaperInfoId'
    })
  },

  destroyed () {
    clearInterval(this.dataTimer)
  },

  mounted () {
    this.getOnlineExamStudentList()
    // 每隔10秒拉取一次数据
    setInterval(() => {
      this.getOnlineExamStudentList()
    }, 10000)
  },

  methods: {

    refresh () {
      this.loading = true
      this.pageNumber = 1
      this.getOnlineExamStudentList()
    },

    currentChangeHandle (val) {
      this.loading = true
      this.pageNumber = val
      this.getOnlineExamStudentList()
    },

    sizeChangeHandle (val) {
      this.loading = true
      this.pageNumber = 1
      this.pageSize = val
      this.getOnlineExamStudentList()
    },

    // 获取数据列表
    getOnlineExamStudentList () {
      this.axios.get(this.$http.httpUrl('/system/examMonitor/paper/' + this.testPaperInfoId), {
        params: {
          pageNumber: this.pageNumber,
          pageSize: this.pageSize
        }
      }).then(response => {
        this.loading = false
        this.onlineExamStudentList = response.data.data.dataList
        this.total = response.data.data.total
      })
    }
  }
}
</script>

<style scoped>

</style>
