<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="query.keyWord" placeholder="试卷名称" clearable></el-input>
      </el-form-item>

      <el-form-item>
        <el-select clearable filterable v-model="query.gradeInfoId" placeholder="请选择年级">
          <el-option
            v-for="item in gradeInfoList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-select
          v-if="query.gradeInfoId"
          clearable filterable
          v-model="query.subjectId"
          placeholder="请选择科目">
          <el-option
            v-for="item in subjectList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button
          v-if="hasPermission(['system:testPaperInfo:list'])"
          @click="searchPaper"
          type="primary" icon="el-icon-search">查询</el-button>
        <el-button
          icon="el-icon-circle-plus-outline"
          type="success" plain
          v-if="hasPermission(['system:testPaperInfo:save'])"
          @click="addPaper">新增试卷</el-button>
        <el-button
          v-if="hasPermission(['system:testPaperInfo:export'])"
          icon="el-icon-upload2"
          plain @click="exportTestPaper">导出</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="testPaperList"
      border
      @selection-change="handleSelectionChange"
      v-loading="loading"
      style="width: 100%;">

      <el-table-column
        type="selection"
        align="center"
        width="50">
      </el-table-column>

      <el-table-column
        prop="name"
        align="center"
        min-width="180"
        :show-overflow-tooltip="true"
        label="试卷名称">
      </el-table-column>

      <el-table-column
        prop="gradeInfoName"
        align="center"
        width="120"
        label="年级">
      </el-table-column>

      <el-table-column
        prop="mark"
        align="center"
        width="80"
        label="总分">
      </el-table-column>

      <el-table-column
        prop="questionNumber"
        align="center"
        width="80"
        label="试题数量">
      </el-table-column>

      <el-table-column
        prop="subjectName"
        align="center"
        width="120"
        label="科目">
      </el-table-column>

      <el-table-column
        align="center"
        width="100"
        label="考试时间">
        <template slot-scope="scope">
          <span>{{scope.row.examTime}}分钟</span>
        </template>
      </el-table-column>

      <el-table-column
        align="center"
        :show-overflow-tooltip="true"
        width="100"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.publishFlag">已发布</el-tag>
          <el-tag v-else type="danger">草稿</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="remark"
        align="center"
        :show-overflow-tooltip="true"
        width="120"
        label="备注">
      </el-table-column>

      <el-table-column
        prop="createDate"
        align="center"
        width="180"
        label="创建时间">
      </el-table-column>

      <el-table-column
        align="center"
        min-width="100"
        label="操作">
          <template slot-scope="scope">
            <el-dropdown>
              <span class="el-dropdown-link">
                操作<i class="el-icon-caret-bottom"></i>
              </span>

              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item
                  v-if="!scope.row.publishFlag && hasPermission(['system:testPaperInfo:publish'])"
                  @click.native="publishTestPaperInfo(scope.row)"
                  icon="el-icon-s-promotion">发布</el-dropdown-item>

                <el-dropdown-item
                  v-if="scope.row.publishFlag"
                  @click.native="goExamMonitor(scope.row)"
                  icon="el-icon-view">考试监控</el-dropdown-item>

                <el-dropdown-item
                  @click.native="printPaper(scope.row)"
                  icon="el-icon-view">预览/打印</el-dropdown-item>

                <el-dropdown-item
                  v-if="scope.row.publishFlag && hasPermission(['system:testPaperInfo:cancel'])"
                  @click.native="cancelTestPaperInfo(scope.row)"
                  icon="el-icon-bottom">撤回</el-dropdown-item>

                <el-dropdown-item
                  v-if="hasPermission(['system:testPaperInfo:relevanceQuestion'])"
                  @click.native="relevanceQuestion(scope.row)"
                  icon="el-icon-tickets">试题管理</el-dropdown-item>

                <el-dropdown-item
                  @click.native="openPaperSetting(scope.row)"
                  icon="el-icon-tickets">试卷设置</el-dropdown-item>

                <el-dropdown-item
                  v-if="!scope.row.publishFlag && hasPermission(['system:testPaperInfo:update'])"
                  @click.native="updateTestPaperInfo(scope.row)"
                  icon="el-icon-edit">修改</el-dropdown-item>

                <el-dropdown-item
                  v-if="!scope.row.publishFlag && hasPermission(['system:testPaperInfo:deleteById'])"
                  @click.native="deleteById(scope.row)"
                  icon="el-icon-delete">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
      </el-table-column>
    </el-table>

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

    <paper-question-dialog-list
       :showFlag="questionListDialog"
       @closeDialog="closeDialog"
       @close="closeForm"
       :gradeTypeList="gradeTypeList"
       :params="queryParams">
    </paper-question-dialog-list>

    <paper-form
      :showFlag="formDialog"
      @closeDialog="closeDialog"
      :formData="paperForm"
      @close="closeForm">
    </paper-form>

    <paper-setting
      :formData="paperSettingForm"
      @closePaperSetting="closePaperSetting"
      @saveCallBack="saveCallBack"
      :showPaperSettingFlag="showPaperSettingFlag">
    </paper-setting>
  </div>
</template>
<script>
  import paperForm from '@/components/education/paper-form'
  import paperSetting from '@/components/education/paper-setting'
  import paperQuestionDialogList from '@/components/education/paper-question-dialog-list'
  import {getGradeInfoList} from '@/api/gradeInfo'
  import {getSubjectInfoList} from '@/api/subjectInfo'
  import {getPaperInfoList} from '@/api/testPaperInfo'
  export default {
    name: 'testPaper',
    components: {paperForm, paperQuestionDialogList, paperSetting},
    data () {
      return {
        questionListDialog: false,
        testPaperList: [],
        gradeInfoList: [],
        queryParams: {
          school_type: '',
          grade_type: '',
          subject_id: ''
        },
        subjectList: [],
        query: {
          keyWord: '',
          gradeInfoId: '',
          subjectId: ''
        },
        checkedTestPaperIds: [],
        keyWord: '',
        exportLoading: null,
        formDialog: false,
        loading: true,
        currentPage: 1,
        pageSize: 10,
        questionList: [],
        totalCount: 0,
        paperForm: {
          name: '',
          gradeInfoId: '',
          schoolType: '',
          remark: '',
          subjectId: '',
          publishFlag: 0,
          sort: 0,
          examTime: null
        },
        showPaperSettingFlag: false,
        paperSettingForm: {
          referenceType: null,
          examType: null,
          testPaperInfoId: null,
          startTime: null,
          endTime: null,
          commitAfterType: '1',
          showMarkSort: 0,
          showStudentSort: true,
          referenceNumber: null
        }
      }
    },

    watch: {

      'query.gradeInfoId' (val) {
        let params = {
          gradeInfoId: val
        }
        getSubjectInfoList(params).then(response => {
          this.subjectList = response.data.data.dataList
        })
      }
    },

    mounted () {
      getGradeInfoList({}).then(response => {
        this.gradeInfoList = response.data.data.dataList
      })
      this.getPaperList()
    },

    methods: {

      closePaperSetting () {
        this.showPaperSettingFlag = false
      },

      saveCallBack () {
        this.showPaperSettingFlag = false
      },

      openPaperSetting (item) {
        this.paperSettingForm.testPaperInfoId = item.id
        this.axios.get(this.$http.httpUrl('/system/testPaper/' + item.id + '/setting'))
          .then(response => {
            let data = response.data.data
            this.showPaperSettingFlag = true
            if (data) {
              this.paperSettingForm = {
                id: data.id,
                testPaperInfoId: item.id,
                examType: data.examType,
                startTime: data.startTime,
                endTime: data.endTime,
                commitAfterType: data.commitAfterType + '',
                showMarkSort: data.showMarkSort,
                showStudentSort: data.showStudentSort === 1,
                referenceNumber: data.referenceNumber,
                referenceType: data.referenceType
              }
            }
          })
      },

      /**
       * 试卷打印
       * @param item
       */
      printPaper (item) {
        const loading = this.$loading({
          lock: true,
          text: '正在生成预览文件',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        this.axios.get(this.$http.httpUrl('/system/testPaperInfo/print/' + item.id))
          .then(response => {
            loading.close()
            window.open(this.$http.getFileHost() + response.data.data)
          })
      },

      /**
       * 发布试卷
       * @param item
       */
      publishTestPaperInfo (item) {
        this.$confirm('确定发布试卷【' + item.name + '】?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.axios.put(this.$http.httpUrl('/system/testPaperInfo/publish/' + item.id))
            .then(response => {
              this.$message.success(response.data.message)
              this.loading = true
              this.getPaperList()
            })
        })
      },

      goExamMonitor (data) {
        this.$store.commit('map/updateTestPaperInfoId', data.id)
        this.$router.push('/examMonitor')
      },

      cancelTestPaperInfo (item) {
        this.$confirm('确定撤回试卷【' + item.name + '】?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.axios.put(this.$http.httpUrl('/system/testPaperInfo/cancel/' + item.id))
            .then(response => {
              this.$message.success(response.data.message)
              this.loading = true
              this.getPaperList()
            })
        })
      },

      handleSizeChange (val) {
        this.currentPage = 1
        this.pageSize = val
        this.loading = true
        this.getPaperList()
      },

      handleSelectionChange (val) {
        this.checkedTestPaperIds = val
      },

      handleCurrentChange (val) {
        this.currentPage = val
        this.loading = true
        this.getPaperList()
      },

      // 点击Dialog 右上角关闭图标时触发
      closeDialog () {
        this.closeForm()
        this.loading = true
        this.getPaperList()
      },

      searchPaper () {
        this.currentPage = 1
        this.loading = true
        this.getPaperList()
      },

      // 试卷导出
      exportTestPaper () {
        let that = this
        if (that.checkedTestPaperIds.length === 0) {
          that.$message.error('请选择试卷导出')
          return
        }
        let ids = []
        let subjectNames = []
        that.checkedTestPaperIds.forEach(testPaperId => {
          ids.push(testPaperId.id)
          subjectNames.push(testPaperId.subject_name)
        })
        let params = {
          'testPaperIds': ids,
          'subjectNames': subjectNames
        }
        that.exportLoading = that.$loading({
          lock: true,
          text: '正在导出试卷试题,请等待。。。',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        that.axios.post(that.$http.httpUrl('/system/testPaper/export'), params).then(function (response) {
          if (response.data.code === 1) {
            that.$message({
              type: 'success',
              message: '导出成功'
            })
            that.exportLoading.close()
            setTimeout(function () {
              location.href = that.$http.getFileHost() + '/' + response.data.message
            }, 500)
          } else {
            that.$message({
              type: 'error',
              message: response.data.message
            })
          }
        })
      },

      updateTestPaperInfo (item) {
        this.formDialog = true
        this.paperForm = {
          id: item.id,
          name: item.name,
          gradeInfoId: item.gradeInfoId,
          schoolType: item.schoolType,
          remark: item.remark,
          subjectId: item.subjectId,
          publishFlag: item.publishFlag ? 1 : 0,
          sort: item.sort,
          examTime: item.examTime
        }
      },

      // 点击Dialog 取消按钮时触发
      closeForm () {
        this.formDialog = false
        this.questionListDialog = false
        this.loading = true
        this.getPaperList()
        this.clearFormData()
      },

      deleteById (item) {
        let that = this
        that.$confirm('确定删除试卷' + item.name + '?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          that.axios.delete(that.$http.httpUrl('/system/testPaperInfo/' + item.id)).then(function (response) {
            if (response.data.code === 1) {
              that.$message({
                type: 'success',
                message: response.data.message
              })
              that.loading = true
              that.getPaperList()
            } else {
              that.$message({
                type: 'error',
                message: response.data.message
              })
            }
          })
        })
      },

      clearFormData () {
        this.paperForm = {
          name: '',
          gradeInfoId: '',
          schoolType: '',
          remark: '',
          subjectId: '',
          publishFlag: 0,
          sort: 0,
          examTime: null
        }
      },

      relevanceQuestion (item) {
        this.queryParams = {
          id: item.id,
          schoolType: item.schoolType,
          subjectId: item.subjectId,
          gradeInfoId: item.gradeInfoId,
          publishFlag: item.publishFlag
        }
        this.questionListDialog = true
      },

      addPaper () {
        this.formDialog = true
      },

      getPaperList () {
        let params = {
          pageNumber: this.currentPage,
          pageSize: this.pageSize,
          name: this.query.keyWord,
          subjectId: this.query.subjectId,
          gradeInfoId: this.query.gradeInfoId
        }

        getPaperInfoList(params).then(response => {
          this.loading = false
          this.testPaperList = response.data.data.dataList
          this.totalCount = response.data.data.total
        })
      }
    },

    filters: {

    }
  }
</script>

