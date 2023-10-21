<template>
  <div class="mod-role">
    <el-form :form="query" :inline="true" @keyup.enter.native="searchDict()">
      <el-form-item>
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="addDict">添加</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="dataList"
      border
      v-loading="loading"
      style="width: 100%">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>

      <el-table-column
        prop="name"
        align="center"
        label="描述">
      </el-table-column>

      <el-table-column
        prop="type"
        align="center"
        label="类型标识">
      </el-table-column>

      <el-table-column
        prop="sort"
        align="center"
        label="排序">
      </el-table-column>

      <el-table-column
        prop="remark"
        align="center"
        label="备注">
      </el-table-column>

      <el-table-column
        prop="createDate"
        align="center"
        label="创建时间">
      </el-table-column>

       <el-table-column min-width="150" align="center" label="操作">
         <template slot-scope="scope">
           <el-button icon="el-icon-edit" @click="editDictValue(scope.row)" type="warning" size="mini">编辑</el-button>
           <el-button icon="el-icon-delete" @click="deleteDictValue(scope.row)" type="danger" size="mini">删除</el-button>
           <el-button icon="el-icon-menu" @click="showDictValue(scope.row)" type="primary" plain size="mini">字典值</el-button>
         </template>
       </el-table-column>
    </el-table>

    <dict-form
      :showFlag="showFlag"
      @closeDialog="closeDialog"
      @close="closeForm"
      :formData="dictForm">
    </dict-form>

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
  import dictForm from '@/components/system/dict-form'
  export default {
    components: {dictForm},
    data () {
      return {
        dataList: [],
        loading: true,
        time: '',
        dictForm: {
          name: '',
          type: '',
          sort: 0,
          remark: ''
        },
        showFlag: false,
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
      this.getDictList()
    },

    methods: {

      addDict () {
        this.showFlag = true
        this.dictForm = {
          name: '',
          type: '',
          sort: 0,
          remark: ''
        }
      },

      showDictValue (item) {
        this.$store.commit('map/updateDictId', item.id)
        this.$router.push({
          name: 'dictValue'
        })
      },

      closeDialog () {
        this.showFlag = false
      },

      closeForm () {
        this.loading = true
        this.showFlag = false
        this.getDictList()
      },

      searchDict () {
        this.currentPage = 1
        this.getDictList()
      },

      handleSizeChange (val) {
        this.pageSize = val
        this.currentPage = 1
        this.getDictList()
      },

      handleCurrentChange (val) {
        this.currentPage = val
        this.getDictList()
      },

      editDictValue (item) {
        this.dictForm = {
          name: item.name,
          type: item.type,
          sort: item.sort,
          remark: item.remark,
          id: item.id
        }
        this.showFlag = true
      },

      handelPotentialRangeChange (val) {
        if (val && val instanceof Array && val.length === 2) {
          this.query.startTime = val[0]
          this.query.endTime = val[1]
        }
      },

      deleteDictValue (item) {
        this.$confirm('确定字典类型【' + item.type + '】?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.axios.delete(this.$http.httpUrl('/api/dict/' + item.id))
              .then(response => {
                this.$message.success(response.data.message)
                this.getDictList()
              })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },

      getDictList () {
        this.axios.get(this.$http.httpUrl('/api/dict'), {
          params: {
            pageNumber: this.currentPage,
            pageSize: this.pageSize
          }
        }).then(response => {
          this.loading = false
          this.dataList = response.data.data.dataList
          this.totalCount = response.data.data.total
        })
      }
    }
  }
</script>
