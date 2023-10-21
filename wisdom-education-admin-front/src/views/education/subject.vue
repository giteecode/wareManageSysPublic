<template>
  <div class="mod-user">
    <el-form :model="query" :inline="true" @keyup.enter.native="searchSubject()">
      <el-form-item>
        <el-select
          v-model="query.schoolType"
          filterable
          clearable
          @change="changeSchoolTypeForQuery"
          placeholder="请选择所属阶段">
          <el-option
            v-for="item in schoolTypeList"
            :key="item.id"
            :label="item.value"
            :value="item.code">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-select
          v-model="query.gradeInfoId"
          filterable
          clearable
          placeholder="请选择年级">
          <el-option
            v-for="item in gradeInfoListForQuery"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-input clearable v-model="query.name" placeholder="输入科目名称" type="text"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button
          v-if="hasPermission(['system:subject:list'])"
          type="success"
          icon="el-icon-search"
          @click="searchSubject()">查询</el-button>

        <el-button
          v-if="hasPermission(['system:subject:update'])"
          type="primary" @click="addSubject">新增</el-button>
       <!-- <el-button v-if="hasPermission(['system:subject:batchDelete'])" @click="batchDelete" type="danger">批量删除</el-button>-->
      </el-form-item>
    </el-form>
    <el-table
      :data="subjectList"
      border
      @select="checkData"
      @selection-change="handleSelectionChange"
      v-loading="loading"
      style="width: 100%;">

      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>

      <el-table-column
        prop="name"
        align="center"
        label="科目名称">
      </el-table-column>

     <!-- <el-table-column
        prop="use_flag"
        align="center"
        label="是否启用">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.use_flag" size="small">是</el-tag>
          <el-tag v-else size="small" type="danger">否</el-tag>
        </template>
      </el-table-column>-->

      <el-table-column
        align="center"
        label="所属阶段">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.schoolType === 1" size="small">小学</el-tag>
          <el-tag v-else-if="scope.row.schoolType === 2" size="small">初中</el-tag>
          <el-tag v-else="scope.row.schoolType === 3" size="small">高中</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        label="所属年级">
        <template slot-scope="scope">
            <el-tag size="small">{{scope.row.gradeName}}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="createDate"
        align="center"
        width="180"
        label="创建时间">
      </el-table-column>

      <el-table-column
        align="center"
        min-width="150"
        label="操作">
        <template slot-scope="scope">

          <el-button
            size="small"
            type="text"
            icon="el-icon-coordinate"
            @click="lookSubjectLanguagePoints(scope.row)">知识点管理</el-button>

          <el-button
            size="small"
            type="text"
            icon="el-icon-edit"
            v-if="hasPermission(['system:subject:update'])"
            @click="updateSubject(scope.row)">修改</el-button>

          <el-button
            size="small"
            icon="el-icon-delete"
            type="text"
            v-if="hasPermission(['system:subject:deleteById'])"
            @click="deleteSubjectById(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :title="!form.id ? '新增科目' : '修改科目'"
      :close-on-click-modal="false"
      :visible.sync="showFlag">

      <el-form :model="form" :rules="rules" ref="form" @keyup.enter.native="saveOrUpdate()" label-width="80px">
        <el-form-item label="科目名称" prop="name">
          <el-input v-model="form.name" placeholder="科目名称"></el-input>
        </el-form-item>


        <el-form-item label="所属阶段" placeholder="所属阶段" prop="schoolType">
          <el-select
            v-model="form.schoolType"
            filterable
            clearable
            placeholder="请选择">
            <el-option
              v-for="item in schoolTypeList"
              :key="item.id"
              :label="item.value"
              :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="所属年级" placeholder="所属年级" prop="gradeInfoId">
          <el-select
            v-model="form.gradeInfoId"
            filterable
            clearable
            placeholder="请选择">
            <el-option
              v-for="item in gradeInfoList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

      <!--  <el-form-item label="是否启用" size="mini">
          <el-radio-group  v-model="form.use_flag" :disabled="disabledUseFlag">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
          <span style="color: red; margin-left: 20px">(注: 当科目设置为启用状态时,阶段,年级,启用状态将无法再次修改)</span>
        </el-form-item>-->
      </el-form>

      <span slot="footer"  class="dialog-footer">
        <el-button @click="showFlag = false">取消</el-button>
        <el-button type="primary" @click="saveOrUpdate()">确定</el-button>
      </span>
    </el-dialog>

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
  </div>
</template>

<script>
  import {getDictValueByType} from '@/api/dict'
  import {getGradeInfoList} from '@/api/gradeInfo'
  import {getSubjectInfoList} from '@/api/subjectInfo'

  export default {
    name: 'subject',
    data () {
      return {
        grade_type: '',
        currentPage: 1,
        pageSize: 10,
        totalCount: 0,
        showFlag: false,
        loading: true,
        schoolTypeList: [],
        gradeInfoList: [],
        gradeInfoListForQuery: [],
        changeGradeTypeList: [],
        subjectList: [],
        form: {
          name: '',
          gradeInfoId: '',
          schoolType: ''
        },
        query: {
          name: '',
          schoolType: '',
          gradeInfoId: ''
        },
        checkIds: [],
        rules: {
          name: [
            {required: true, message: '请输入科目名称', trigger: 'blur'}
          ],
          schoolType: [
            {required: true, message: '请选择所属阶段', trigger: 'blur'}
          ],
          gradeInfoId: [
            {required: true, message: '请选择所属年级', trigger: 'blur'}
          ]
        }
      }
    },

    watch: {
      'form.schoolType' (val) {
        let params = {
          schoolType: val
        }
        getGradeInfoList(params).then(response => {
          this.gradeInfoList = response.data.data.dataList
        })
      }
    },

    mounted () {
      getDictValueByType('school_type').then(response => {
        this.schoolTypeList = response.data.data
      })
      this.getList()
    },

    methods: {

      lookSubjectLanguagePoints (subject) {
        this.$store.commit('map/updateSubjectInfo', subject)
        this.$router.push({
          name: 'languagePoints'
        })
      },

      getList () {
        let params = {
          pageNumber: this.currentPage,
          pageSize: this.pageSize,
          name: this.query.name,
          schoolType: this.query.schoolType,
          gradeInfoId: this.query.gradeInfoId
        }
        getSubjectInfoList(params).then(response => {
          this.loading = false
          this.subjectList = response.data.data.dataList
          this.totalCount = response.data.data.total
        })
      },

      searchSubject () {
        this.currentPage = 1
        this.loading = true
        this.getList()
      },

      changeSchoolTypeForQuery (val) {
        let params = {
          schoolType: val
        }
        getGradeInfoList(params).then(response => {
          this.gradeInfoListForQuery = response.data.data.dataList
        })
      },

      clearFormData () {
        this.form = {
          name: '',
          gradeTypeId: '',
          schoolType: ''
        }
      },

      addSubject () {
        this.clearFormData()
        this.showFlag = true
      },

      handleSelectionChange (val) {
        this.checkIds = val
      },

      checkData (select) {
        console.log(select)
      },

      batchDelete () { // 批量删除

      },

      sizeChangeHandler (val) {
        this.pageSize = val
        this.currentPage = 1
        this.loading = true
        this.getList()
      },

      currentChangeHandler (val) {
        this.currentPage = val
        this.loading = true
        this.getList()
      },

      saveOrUpdate () {
        let that = this
        that.$refs['form'].validate(function (valid, rules) {
          if (valid) {
            that.axios.post(that.$http.httpUrl('/system/subject'), that.form)
              .then(function (response) {
                that.showFlag = false // 必须在加载列表之前，否则会出现filters 中方法获取年级异常bug
                if (response.data.code === 1) {
                  that.$message({
                    type: 'success',
                    message: response.data.message
                  })

                  that.clearFormData()
                  that.getList()
                }
              }).catch(function (error) {
                console.log(error)
              })
          }
        })
      },

      // 注意 不能直接将item赋值给form
      updateSubject (item) {
        this.form = {
          id: item.id,
          name: item.name,
          gradeInfoId: item.gradeInfoId,
          schoolType: item.schoolType
        }
        this.showFlag = true
      },

      deleteSubjectById (item) {
        let that = this
        that.$confirm('确定删除科目【' + item.name + '】?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          that.axios.delete(that.$http.httpUrl('/system/subject/' + item.id)).then(function (response) {
            if (response.data.code === 1) {
              that.$message({
                type: 'success',
                message: response.data.message
              })
              that.getList()
            }
          })
        })
      }
    },

    filters: {

    }
  }
</script>
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 90px;
    height: 90px;
    line-height: 90px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
