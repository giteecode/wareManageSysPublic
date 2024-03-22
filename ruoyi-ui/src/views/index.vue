<template>
  <div class="dashboard-editor-container">

    <panel-group />

    <el-row>
      <el-col :xs="24" :sm="24" :lg="16">
        <div class="chart-wrapper left">
          <bar-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <el-calendar v-model="today"></el-calendar>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :xs="24" :sm="24" :lg="16">
        <div class="chart-wrapper left">
          <!-- <line-chart /> -->
          <el-table :data="recordList" style="width: 100%">
            <el-table-column prop="createTime" label="时间">
              <template slot-scope="scope">
                <span>{{$moment(scope.row.createTime).format('YYYY-MM-DD HH:mm')}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="orderNo" align="center" label="单据号" width="180"></el-table-column>
            <el-table-column prop="matCode" align="center" label="物料编码"></el-table-column>
            <el-table-column prop="matName" align="center" label="物料名称"></el-table-column>
            <el-table-column prop="quantity" align="center" label="数量" width="80"></el-table-column>
            <el-table-column prop="recordTypeLabel" align="center" label="类型"></el-table-column>
            <el-table-column prop="createBy" align="center" label="库管员" width="100"></el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <!-- <line-chart /> -->
          <el-table :data="logininforList" style="width: 100%">
            <el-table-column prop="loginTime" label="时间">
              <template slot-scope="scope">
                <span>{{$moment(scope.row.loginTime).format('YYYY-MM-DD HH:mm')}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="userName" label="账号"></el-table-column>
            <el-table-column prop="msg" label="操作"></el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
    
  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'

import { getLogininfor } from "@/api/login";
import { listRecord } from "@/api/stock/record";

export default {
  name: 'Index',
  components: { PanelGroup, LineChart, RaddarChart, PieChart, BarChart },
  data() {
    return {
      today: new Date(),
      logininforList: [],
      recordList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        status: 0,
      },
    }
  },
  created() {
    this.loginData();
    this.recordData();
  },
  methods: {
    loginData(){
      getLogininfor(this.queryParams).then(response => {
        this.logininforList = response.data;
      });
    },
    recordData(){
      listRecord(this.queryParams).then(response => {
        this.recordList = response.rows;
      });
    },
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 10px 20px 0 20px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 10px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
.left{
  margin-right: 10px;
}

::v-deep .el-calendar-day{
  box-sizing: border-box;
  padding: 8px;
  height: 30px !important;
}
::v-deep .el-calendar__body {
    padding: 12px 20px 25px;
}
</style>
