<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="bomList">
      <el-table-column label="物料编码" align="center" prop="childMatCode" />
      <el-table-column label="物料名称" align="center" prop="childMatName" />
      <el-table-column label="图号" align="center" prop="figNum" />
      <el-table-column label="数量" align="center" prop="childMatNum" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" v-if="scope.row.isFictitious === 'Y'">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleDetail(scope.row)"
          >详情</el-button>
        </template>
        <template slot-scope="scope" v-if="scope.row.isFictitious === 'N'">
          <span>-</span>
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
import { detailListBom } from "@/api/base/bom";

export default {
  name: "Bom",
  props: {
    fatherMatCode: {
      type: String,
      default: 0,
    }
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 物料BOM表格数据
      bomList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fatherMatCode: null,
        childMatCode: null,
        childMatName: null,
      },
    };
  },
  created() {
    this.queryParams.fatherMatCode = this.fatherMatCode;
    this.getList();
  },
  methods: {
    /** 查询物料BOM列表 */
    getList() {
      this.loading = true;
      detailListBom(this.queryParams).then(response => {
        this.bomList = response.rows;
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
    //详情
    handleDetail(row){

    },
  }
};
</script>

<style scoped>
.app-container{
  padding: 0px;
}
</style>