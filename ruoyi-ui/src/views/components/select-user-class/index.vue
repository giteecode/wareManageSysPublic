<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分类编码" prop="classCode">
        <el-input
          v-model="queryParams.classCode"
          placeholder="请输入物料分类编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类名称" prop="className">
        <el-input
          v-model="queryParams.className"
          placeholder="请输入物料分类名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="classList">
      <el-table-column label="分类编码" align="center" prop="classCode" />
      <el-table-column label="分类名称" align="center" prop="className" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.activeStatus" active-color="#13ce66" inactive-color="#9d9d9d" 
            active-value="1" inactive-value="0" @change="handleSelectChange(scope.row)">
          </el-switch>
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
import { listClass, changeActive } from "@/api/system/userClass";

export default {
  name: "Class",
  props: {
    userName: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用户管理的物料分类表格数据
      classList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        classCode: null,
        className: null,
      },
    };
  },
  created() {
    this.queryParams.userName = this.userName;
    this.getList();
  },
  methods: {
    /** 查询用户管理的物料分类列表 */
    getList() {
      this.loading = true;
      listClass(this.queryParams).then(response => {
        this.classList = response.rows;
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
    handleSelectChange(row){
      let text = row.activeStatus === "0" ? "去除" : "关联";
      let data = {
        userName: this.userName,
        classCode: row.classCode,
      };
      this.$modal.confirm('确认要 "' + text + '" 物料组吗？').then(function() {
        return changeActive(data);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.activeStatus = row.activeStatus === "0" ? "1" : "0";
      });
    },
  }
};
</script>
