<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编码" prop="supplierCode">
        <el-input
          v-model="queryParams.supplierCode"
          placeholder="请输入供应商编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系方式" prop="contact">
        <el-input
          v-model="queryParams.contact"
          placeholder="请输入联系方式"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否合格" prop="isQualified">
        <el-input
          v-model="queryParams.isQualified"
          placeholder="请输入是否合格供应商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="supplierList" style="width: 100%">
      <el-table-column label="供应商编码" fixed align="center" prop="supplierCode" width="100" />
      <el-table-column label="供应商名称" fixed align="center" prop="supplierName" width="180" />
      <el-table-column label="供货名称" align="center" prop="supplyType" width="120" />
      <el-table-column label="地址" align="center" prop="address" width="180" />
      <el-table-column label="联系方式" align="center" prop="contact" width="120" />
      <el-table-column label="税号" align="center" prop="taxNumber" width="120" />
      <el-table-column label="开户行" align="center" prop="depositBank" width="120" />
      <el-table-column label="账号" align="center" prop="bankAccount" width="120" />
      <el-table-column label="是否合格" align="center" prop="isQualified" width="80" />
      <el-table-column label="城市" align="center" prop="city" width="80" />
      <el-table-column label="邮政编码" align="center" prop="postalCode" width="80" />
      <el-table-column label="操作" fixed="right" align="center" width="120" class-name="small-padding fixed-width">
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
import { listSupplier } from "@/api/base/supplier";

export default {
  name: "Supplier",
  dicts: ['sys_yes_no'],
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
      // 供应商表格数据
      supplierList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        supplierCode: null,
        supplierName: null,
        supplyType: null,
        address: null,
        contact: null,
        taxNumber: null,
        depositBank: null,
        bankAccount: null,
        isQualified: null,
        city: null,
        postalCode: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询供应商列表 */
    getList() {
      this.loading = true;
      listSupplier(this.queryParams).then(response => {
        this.supplierList = response.rows;
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
        supplierId: null,
        supplierCode: null,
        supplierName: null,
        supplyType: null,
        address: null,
        contact: null,
        taxNumber: null,
        depositBank: null,
        bankAccount: null,
        isQualified: null,
        city: null,
        postalCode: null,
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
    //确认选择
    handleSelect(row){
      let that = this;
      this.$modal.confirm('确认选择 "' + row.supplierName + '" 供应商？').then(function() {
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