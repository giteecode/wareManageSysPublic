<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-button
          icon="el-icon-circle-plus" @click="addAdvertisement">新增</el-button>
      </el-form-item>

      </el-form>
      <el-table
        :data="advertisementList"
        border
        v-loading="loading"
        style="width: 100%;">

      <el-table-column
        align="center"
        label="广告图片">
        <template slot-scope="scope">
           <img v-if="scope.row.imgUrl" style="width: 40px; height: 40px;" :src="fileUrl + scope.row.imgUrl"/>
        </template>
      </el-table-column>

      <el-table-column
        prop="link"
        min-width="100"
        align="center"
        label="链接">
      </el-table-column>

      <el-table-column
        prop="createDate"
        align="center"
        min-width="120"
        label="创建时间">
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        min-width="120"
        label="操作">
        <template slot-scope="scope">
          <el-button
            @click="updateAdvertisement(scope.row)"
            icon="el-icon-edit"
            type="text">修改</el-button>
          <el-button
            @click="deleteById(scope.row)"
            icon="el-icon-edit"
            type="text">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      @size-change="sizeChangeHandler"
      @current-change="currentChangeHandler"
      :current-page="currentPage"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="pageSize"
      :total="totalCount"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>

    <advertisement-form
      :showFlag="formDialog"
      @closeDialog="closeDialog"
      @close="closeForm"
      :formData="advertisementForm">
    </advertisement-form>
  </div>
</template>
<script>
  import advertisementForm from '@/components/system/advertisement-form'
  import PlatformEnum from '@/enum/PlatformEnum'
  import {listPage} from '@/api/advertisement'
  export default {
    name: 'advertisement',
    components: {advertisementForm},
    data () {
      return {
        PlatformEnum,
        platformType: [],
        formDialog: false,
        advertisementList: [],
        fileUrl: this.$http.getFileHost(),
        advertisementForm: {
          place: '',
          link: '',
          imgUrl: ''
        },
        loading: true,
        currentPage: 1,
        pageSize: 10,
        totalCount: 0
      }
    },

    mounted () {
      this.getListPage()
      /* PlatformEnum.keys().forEach(item => {
        this.platformType.push({
          'code': item.value.code,
          'value': item.value.value
        })
      }) */
    },

    methods: {

      getListPage () {
        let params = {
          pageNumber: this.currentPage,
          pageSize: this.pageSize
        }
        listPage(params).then(response => {
          if (response.data.code === 1) {
            this.advertisementList = response.data.data.dataList
            this.totalCount = response.data.data.total
            this.loading = false
          }
        })
      },

      sizeChangeHandler (val) {
        this.pageSize = val
        this.currentPage = 1
        this.getListPage()
      },

      currentChangeHandler (val) {
        this.currentPage = val
        this.getListPage()
      },

      addAdvertisement () {
        this.formDialog = true
        this.clearFormData()
      },

      clearFormData () {
        this.advertisementForm = {
          place: '',
          link: '',
          imgUrl: ''
        }
      },

      deleteById (item) {

      },

      closeForm () {
        this.formDialog = false
        this.getListPage()
        this.clearFormData()
      },

      closeDialog () {
        this.formDialog = false
      },

      updateAdvertisement (item) {
        this.advertisementForm = {
          id: item.id,
          place: item.place,
          link: item.link,
          imgUrl: item.imgUrl
        }
        this.formDialog = true
      }
    },

    filters: {

    }
  }
</script>
