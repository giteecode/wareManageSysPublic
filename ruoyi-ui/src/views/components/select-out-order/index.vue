<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单据号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入单据号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单据状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择">
          <el-option
            v-for="item in orderStatusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="生产订单" v-if="outOrderType==='production'" prop="prodOrderNo">
        <el-input
          v-model="queryParams.prodOrderNo"
          placeholder="请输入生产订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseCode">
        <el-select v-model="queryParams.warehouseCode" placeholder="请选择仓库">
          <el-option
            v-for="item in warehouseList"
            :key="item.warehouseCode"
            :label="item.warehouseName"
            :value="item.warehouseCode"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="车间" prop="workshopCode">
        <el-select v-model="queryParams.workshopCode" placeholder="请选择车间">
          <el-option
            v-for="item in workshopList"
            :key="item.workshopCode"
            :label="item.workshopName"
            :value="item.workshopCode"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="物料编码" prop="matCode">
        <el-input
          v-model="queryParams.matCode"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="matName">
        <el-input
          v-model="queryParams.matName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="库管员" v-if="outOrderType==='production'" prop="warehouseKeeper">
        <el-input
          v-model="queryParams.warehouseKeeper"
          placeholder="请输入库管员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="outOrderList" style="width: 100%">
      <el-table-column label="单据号" fixed align="center" prop="orderNo" width="180" />
      <el-table-column label="单据类型" fixed align="center" prop="orderTypeLabel" width="120" />
      <el-table-column label="生产订单号" v-if="outOrderType==='production'" align="center" prop="prodOrderNo" width="180" />
      <el-table-column label="物料编码" v-if="outOrderType==='production'" align="center" prop="matCode" width="120" />
      <el-table-column label="物料名称" v-if="outOrderType==='production'" align="center" prop="matName" width="180" />
      <el-table-column label="仓库" align="center" prop="warehouseName" width="120" />
      <el-table-column label="库管员" v-if="outOrderType==='production'" align="center" prop="warehouseKeeper" width="120" />
      <el-table-column label="车间" align="center" prop="workshopName" width="120" />
      <el-table-column label="单据状态" align="center" prop="orderStatusLabel" width="120" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{$moment(scope.row.createTime).format('YYYY-MM-DD HH:mm')}}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" align="center" class-name="small-padding fixed-width" width="120">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleSelect(scope.row)"
          >选择</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>

<script>
import { listOutOrder } from "@/api/stock/outOrder";
import { listAllWorkshop } from "@/api/base/workshop";
import { listAllWarehouse } from "@/api/base/warehouse";


export default {
  name: "OutOrder",
  dicts: ['base_mat_unit'],
  props: {
    outOrderType:{
      type: String,
      default: 'production',
    },
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 出库单表格数据
      outOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        orderType: 'production',
        prodOrderNo: null,
        warehouseCode: null,
        workshopCode: null,
        orderReason: null,
        matCode: null,
        matName: null,
        quantity: null,
        orderStatus: null,
        warehouseKeeper: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },

      // 日期范围
      dateRange: [],

      //单据状态
      orderStatusOptions:[
        {value: 'created', label: '已创建'}, 
        {value: 'printed', label: '已打印'},
        {value: 'received', label: '已领料'},
      ],

      //选择仓库、车间
      warehouseList: [],
      workshopList: [],

    };
  },
  created() {
    this.queryParams.orderType = this.outOrderType;
    this.getList();
    this.getWarehouseList();
    this.getWorkshopList();
  },
  methods: {
    /** 查询出库单列表 */
    getList() {
      this.loading = true;
      listOutOrder(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.outOrderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //查询仓库、车间
    getWarehouseList(){
      listAllWarehouse().then(response => {
        this.warehouseList = response;
      });
    },
    getWorkshopList(){
      listAllWorkshop().then(response => {
        this.workshopList = response;
      });
    },
    //确认选择
    handleSelect(row){
      let that = this;
      this.$modal.confirm('确认选择 "' + row.orderNo + '" 出库单？').then(function() {
        that.$emit("confirmSelect", row);
      });
    },
  }
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.detail-dialog{
  .el-form-item{
    margin-bottom: 0px;
  }
  .detail-table{
    margin-top: 20px;
  }
}
</style>

<style scoped>
.app-container{
  padding: 0px;
}
</style>