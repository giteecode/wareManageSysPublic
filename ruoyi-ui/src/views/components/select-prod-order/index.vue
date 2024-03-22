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
      <el-form-item label="工令号" prop="workNo">
        <el-input
          v-model="queryParams.workNo"
          placeholder="请输入工令号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择">
          <el-option
            v-for="item in orderStatusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="prodOrderList">
      <el-table-column label="单据号" align="center" prop="orderNo" />
      <el-table-column label="工令号" align="center" prop="workNo" />
      <el-table-column label="车间" align="center" prop="workshopName" />
      <el-table-column label="物料编码" align="center" prop="matCode" />
      <el-table-column label="物料名称" align="center" prop="matName" />
      <el-table-column label="数量" align="center" prop="quantity" />
      <el-table-column label="状态" align="center" prop="orderStatusLabel" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
import { listProdOrder } from "@/api/stock/prodOrder";
import { listAllWorkshop } from "@/api/base/workshop";
import selectMatBom from "../../components/select-mat-bom/index"

export default {
  name: "ProdOrder",
  components: { selectMatBom },
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
      // 生产订单表格数据
      prodOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        workNo: null,
        matCode: null,
        matName: null,
        workshopCode: null,
        quantity: null,
        orderStatus: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },

      //车间
      workshopList: [],

      //选择物料
      selectMatOpen: false,

      //生产订单状态
      orderStatusOptions:[{value: 'ongoing', label: '进行中'}, {value: 'finished', label: '已完成'}],
    };
  },
  created() {
    this.getList();
    this.getWorkshopList();
  },
  methods: {
    /** 查询生产订单列表 */
    getList() {
      this.loading = true;
      listProdOrder(this.queryParams).then(response => {
        this.prodOrderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        orderId: null,
        orderNo: null,
        workNo: null,
        matCode: null,
        matName: null,
        workshopCode: null,
        quantity: null,
        orderStatus: "0",
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //查询车间
    getWorkshopList(){
      listAllWorkshop().then(response => {
        this.workshopList = response;
      });
    },
    //确认选择
    handleSelect(row){
      let that = this;
      this.$modal.confirm('确认选择 "' + row.orderNo + '" 生产订单？').then(function() {
        that.$emit("confirmSelect", row);
      });
    },
  }
};
</script>

<style scoped>
.app-container{
  padding: 0px;
}
</style>