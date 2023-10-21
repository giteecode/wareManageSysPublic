<template>
  <div class="mod-role">
    <el-form :form="query" :inline="true">
      <el-form-item>
        <el-button type="primary" icon="el-icon-circle-plus-outline" @click="addDict">添加</el-button>
      </el-form-item>

   <!--   <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="searchLogs">查询</el-button>
      </el-form-item>-->
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
        prop="value"
        align="center"
        label="字典值描述">
      </el-table-column>

      <el-table-column
        prop="code"
        align="center"
        label="字典值">
      </el-table-column>


      <el-table-column
        prop="sort"
        align="center"
        label="排序">
      </el-table-column>

      <el-table-column min-width="150" align="center" label="操作">
        <template slot-scope="scope">
          <el-button icon="el-icon-edit" @click="editDictValueById(scope.row)" type="warning" size="mini">编辑</el-button>
          <el-button icon="el-icon-delete" @click="deleteDictValueById(scope.row)" type="danger" size="mini">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <dict-value-form
      :showFlag="showFlag"
      :system_dict_id="dictId"
      @closeDialog="closeDialog"
      @close="closeForm"
      :formData="dictForm">
    </dict-value-form>

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
  import dictValueForm from '@/components/system/dict-value-form'
  import { mapGetters } from 'vuex'
  export default {
    components: {dictValueForm},
    data () {
      return {
        dataList: [],
        loading: true,
       // dictId: 0,
        time: '',
        dictForm: {
          code: '',
          value: ''
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

    computed: {
      ...mapGetters({
        dictId: 'map/getDictId'
      })
    },

    mounted () {
      this.dictId = parseInt(this.dictId)
      this.getDictValueListById()
    },

    methods: {

      addDict () {
        this.showFlag = true
      },

      closeDialog () {
        this.showFlag = false
        this.getDictValueListById()
      },

      closeForm () {
        this.dictForm = {
          code: '',
          value: ''
        }
        this.showFlag = false
        this.getDictValueListById()
      },

      handleSizeChange (val) {
        this.pageSize = val
        this.currentPage = 1
        this.getDictValueListById()
      },

      handleCurrentChange (val) {
        this.currentPage = val
        this.getDictValueListById()
      },

      deleteDictValueById (item) {
        let that = this
        that.$confirm('确定删除字典值【' + item.value + '】?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          that.axios.delete(that.$http.httpUrl('/api/dictValue/' + item.id)).then(response => {
            if (response.data.code === 1) {
              that.$message.success(response.data.message)
              that.getDictValueListById()
            }
          })
        })
      },

      editDictValueById (item) {
        this.dictForm = {
          code: item.code,
          value: item.value,
          id: item.id,
          sort: item.sort,
          parent_id: item.parent_id,
          parentArrayId: []
        }
        if (item.parent_id !== 0) {
          this.dictForm.parentArrayId.push(item.parent_id)
            //  this.dictForm.parentArrayId.push(item.id)
        }
        this.showFlag = true
      },

      getDictValueListById () {
        this.axios.get(this.$http.httpUrl('/api/dictValue/selectByDictId'), {
          params: {
            pageNumber: this.currentPage,
            pageSize: this.pageSize,
            dictId: this.dictId
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
