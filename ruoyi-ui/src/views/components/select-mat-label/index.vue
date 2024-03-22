<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料编码" prop="matCode">
        <el-input
          v-model="queryParams.matCode"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料描述" prop="matName">
        <el-input
          v-model="queryParams.matName"
          placeholder="请输入物料描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="财务编码" prop="fdCode">
        <el-input
          v-model="queryParams.fdCode"
          placeholder="请输入财务编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图号" prop="figNum">
        <el-input
          v-model="queryParams.figNum"
          placeholder="请输入图号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料组" prop="matGroup">
        <el-select v-model="queryParams.matGroup" placeholder="请选择物料组">
          <el-option
            v-for="item in groupList"
            :key="item.groupCode"
            :label="item.groupName"
            :value="item.groupCode"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="分类" prop="matClass">
        <el-select v-model="queryParams.matClass" placeholder="请选择物料分类">
          <el-option
            v-for="item in classList"
            :key="item.classCode"
            :label="item.className"
            :value="item.classCode"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="批次" prop="batch">
        <el-input
          v-model="queryParams.batch"
          placeholder="请输入批次"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产时间" prop="prodTime">
        <el-date-picker clearable
          v-model="queryParams.prodTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择生产时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleSelectArr"
        >选择</el-button>
      </el-col>
    </el-row>

    <el-table ref="selectMatLabelTable" v-loading="loading" :data="matLabelList" style="width: 100%">
      <el-table-column type="selection" :selectable="checkSelectable" width="55" align="center" />
      <el-table-column label="物料编码" fixed align="center" prop="matCode" width="100" />
      <el-table-column label="物料描述" fixed align="center" prop="matName" width="120" />
      <el-table-column label="财务编码" align="center" prop="fdCode" width="100" />
      <el-table-column label="图号" align="center" prop="figNum" width="150" />
      <el-table-column label="物料组" align="center" prop="matGroupName" width="80" />
      <el-table-column label="分类" align="center" prop="matClassName" width="80" />
      <el-table-column label="数量" align="center" prop="quantity" width="80" />
      <el-table-column label="单价" align="center" prop="unitPrice" width="80" />
      <el-table-column label="基本单位" align="center" prop="unitCode" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.base_mat_unit" :value="scope.row.unitCode"/>
        </template>
      </el-table-column>
      <el-table-column label="批次" align="center" prop="batch" width="180" />
      <el-table-column label="供应商" align="center" prop="supplierName" width="180" />
      <el-table-column label="生产时间" align="center" prop="prodTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.prodTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入库单号" align="center" prop="orderNo" width="120" />
      <el-table-column label="操作" fixed="right" align="center" width="120" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="labelIdArr.indexOf(scope.row.labelId) < 0"
            size="mini"
            type="text"
            icon="el-icon-c-scale-to-original"
            @click="handleSelect(scope.row)"
          >选择</el-button>

          <span v-if="labelIdArr.indexOf(scope.row.labelId) >= 0">已选</span>
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
import { listMatLabelDialog } from "@/api/stock/matLabel";
import { listAllGroup } from "@/api/base/group";
import { listAllClass } from "@/api/base/class";

export default {
  name: "MatLabel",
  dicts: ['base_mat_unit'],
  props: {
    labelIdArr:{
      type: Array,
      default: [],
    }
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
      // 物料标签表格数据
      matLabelList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        labelType: null,
        matCode: null,
        matName: null,
        fdCode: null,
        figNum: null,
        matGroup: null,
        matClass: null,
        unitCode: null,
        batch: null,
        supplierCode: null,
        supplierName: null,
        prodTime: null,
        quantity: null,
        unitPrice: null,
        orderNo: null,
        orderType: null,
      },

      //组、分类
      groupList: [],
      classList: [],

    };
  },
  created() {
    // this.getList();
    this.getGroupList();
    this.getClassList();
  },
  methods: {
    /** 查询物料标签列表 */
    getList() {
      this.loading = true;
      listMatLabelDialog(this.queryParams).then(response => {
        this.matLabelList = response.rows;
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    //是否可选
    checkSelectable(row, index){
      if(this.labelIdArr.indexOf(row.labelId) >= 0){
        return false;
      }else{
        return true;
      }
    },
    //选择物料
    handleSelect(row){
      this.$refs.selectMatLabelTable.clearSelection();
      this.$emit('confirmSelect', row);
    },
    //多选物料
    handleSelectArr(){
      let arr = this.$refs.selectMatLabelTable.selection;
      this.$refs.selectMatLabelTable.clearSelection();
      this.$emit('confirmSelectArr', arr);
    },
    /** 查询组、分类 */
    getGroupList(){
      listAllGroup().then(response => {
        this.groupList = response;
      });
    },
    getClassList(){
      listAllClass().then(response => {
        this.classList = response;
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