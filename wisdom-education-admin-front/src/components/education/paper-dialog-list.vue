<template>
    <div>
      <el-dialog
        width="70%"
        @closed="closeTestPaperDialog"
        title="试卷管理"
        :visible.sync="testPaperDialogVisible">
        <el-form :inline="true">
          <el-form-item>
            <el-button type="primary" plain @click="addTestPaper" icon="el-icon-plus">试卷库导入</el-button>
          </el-form-item>
        </el-form>

        <el-table style="width: 100%" :data="testPaperListForMode">
          <el-table-column
            prop="name"
            :show-overflow-tooltip="true"
            label="试卷名称"
            min-width="150">
          </el-table-column>

          <el-table-column
            :show-overflow-tooltip="true"
            label="阶段"
            min-width="150">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.school_type === 1" size="small">小学</el-tag>
              <el-tag v-else-if="scope.row.school_type === 2" size="small">初中</el-tag>
              <el-tag v-else-if="scope.row.school_type === 3" size="small">高中</el-tag>
            </template>
          </el-table-column>

          <el-table-column
            align="center"
            width="120"
            label="年级">
            <template slot-scope="scope">
              <el-tag size="small">{{scope.row.grade_type | getGradeName}}</el-tag>
            </template>
          </el-table-column>

          <el-table-column
            prop="mark"
            align="center"
            width="80"
            label="总分">
          </el-table-column>

          <el-table-column
            prop="subject_name"
            align="center"
            width="120"
            label="科目">
          </el-table-column>

          <el-table-column
            prop="remark"
            align="center"
            :show-overflow-tooltip="true"
            width="120"
            label="备注">
          </el-table-column>

          <el-table-column
            align="sort"
            label="排序"
            width="100">
            <template slot-scope="scope">
              <el-input @blur="changePaperSort(scope.row)" v-model="scope.row.sort"/>
            </template>
          </el-table-column>

          <el-table-column
            align="center"
            width="250"
            label="操作">
            <template slot-scope="scope">
              <el-button
                size="small"
                @click="deleteTestPaperForMode(scope.row)"
                type="danger">移除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          background
          @current-change="modePaperHandleCurrentPage"
          :current-page="currentPage"
          :page-size="10"
          :total="totalCount"
          layout="total, prev, pager, next, jumper">
        </el-pagination>

      </el-dialog>

      <el-dialog width="70%"   title="试卷导入" :visible.sync="testPaperDialog">
        <el-table @selection-change="testPaperHandleSelectionChange" :data="testPaperList">
          <el-table-column
            type="selection"
            align="center"
            width="50">
          </el-table-column>

          <el-table-column
            prop="name"
            :show-overflow-tooltip="true"
            label="试卷名称"
            min-width="150">
          </el-table-column>

          <el-table-column
            prop="school_type"
            width="120"
            label="阶段">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.school_type === 1" size="small">小学</el-tag>
              <el-tag v-else-if="scope.row.school_type === 2" size="small">初中</el-tag>
              <el-tag v-else-if="scope.row.school_type === 3" size="small">高中</el-tag>
            </template>
          </el-table-column>

          <el-table-column
            prop="grade_type"
            width="120"
            label="年级">
            <template slot-scope="scope">
              <el-tag size="small">{{scope.row.grade_type | getGradeName}}</el-tag>
            </template>
          </el-table-column>

          <el-table-column
            prop="subject_name"
            label="科目"
            width="120">
          </el-table-column>

          <el-table-column
            prop="mark"
            label="总分"
            width="120">
          </el-table-column>

        </el-table>

        <el-pagination
          background
          @current-change="testPaperHandleCurrentPage"
          :current-page="testPaperCurrentPage"
          :page-size="10"
          :total="testPaperTotalCount"
          layout="total, prev, pager, next, jumper">
        </el-pagination>

        <div slot="footer" class="dialog-footer">
          <el-button @click="questionDialog = false">取 消</el-button>
          <el-button @click="saveRelevanceTestPaper" type="primary">确 定</el-button>
        </div>
      </el-dialog>

    </div>
</template>

<script>
  import { getGradeType } from '@/utils/education-api'
  let thisPage = null
  export default {
    name: 'paper-dialog-list',
    props: {
      showFlag: {
        type: Boolean,
        required: false,
        default: false
      },

      params: {
        type: Object,
        required: true,
        default: {}
      }
    },

    watch: {
      params (val) {
        this.queryParams = val
        this.getTestPaperForMode()
      },

      showFlag (val) {
        this.testPaperDialogVisible = val
      }
    },
    data () {
      return {
        currentPage: 1,
        checkedTestPaperIds: [],
        pageSize: 10,
        testPaperDialog: false,
        totalCount: 0,
        testPaperCurrentPage: 1,
        testPaperListForMode: [],
        testPaperList: [],
        queryParams: {},
        testPaperDialogVisible: false,
        testPaperTotalCount: 0,
        testPaperCheckedIds: []
      }
    },

    beforeCreate () {
      thisPage = this
    },

    mounted () {
      let that = this
      that.queryParams = that.params
      that.testPaperDialogVisible = that.showFlag
      getGradeType(function (data) {
        that.gradeTypeList = data
      })
    },

    methods: {

      // 排序
      changePaperSort (row) {
        delete (row.subject_name)
        this.axios.post(this.$http.httpUrl('/system/testPaper/saveOrUpdate'), row)
          .then((response) => {
            if (response.data.code === 1) {
              this.$message.success('操作成功')
              this.getTestPaperForMode()
            } else {
              this.$message({
                type: 'error',
                message: response.data.message
              })
            }
          }).catch(function (error) {
            console.log(error)
          })
      },

      modePaperHandleCurrentPage (val) {
        this.currentPage = val
        this.getTestPaperForMode()
      },

      addTestPaper () {
        this.getAllTestPaper()
        this.testPaperDialog = true
      },

      testPaperHandleSelectionChange (val) {
        this.checkedTestPaperIds = val
      },

      testPaperHandleCurrentPage (val) {
        this.testPaperCurrentPage = val
      },

      // 获取所有试卷列表
      getAllTestPaper () {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/pagination'), {
          params: {
            sqlId: 'test.paper.info.list',
            subjectId: that.queryParams.subject_id,
            gradeType: that.queryParams.grade_type,
            currentPage: that.testPaperCurrentPage,
            offset: (that.testPaperCurrentPage - 1) * 10,
            limit: that.pageSize
          }
        }).then(function (response) {
          that.testPaperList = response.data.data
          that.testPaperTotalCount = response.data.total
        })
      },

      closeTestPaperDialog () {
        this.dialogVisible = false
        this.$emit('closeModeTestPaperDialog')
      },

      deleteTestPaperForMode (item) {
        let that = this
        that.$confirm('确定删除试卷' + item.name + '?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          let params = {
            modeId: that.queryParams.id,
            testPaperInfoId: item.id
          }
          that.axios.delete(that.$http.httpUrl('/system/mode/deleteModePaper'), {
            data: params
          })
            .then(function (response) {
              if (response.data.code === 1) {
                that.$message.success(response.data.message)
                that.getTestPaperForMode()
              } else {
                that.$message.error(response.data.message)
              }
            }).catch(function (error) {
              console.log(error)
            })
        }).catch(function (e) {

        })
      },

      saveRelevanceTestPaper () {
        let that = this
        if (that.checkedTestPaperIds.length === 0) {
          that.$message.error('请选择试卷')
          return
        }
        let testPaperIds = []
        that.checkedTestPaperIds.forEach(function (testPaper) {
          testPaperIds.push(testPaper.id)
        })
        let params = {
          modeId: that.queryParams.id,
          testPaperIds: testPaperIds
        }

        that.axios.post(that.$http.httpUrl('/system/mode/relevanceTestPaper'), params)
          .then(function (response) {
            if (response.data.code === 1) {
              that.testPaperDialog = false
              that.$message.success(response.data.message)
              that.getTestPaperForMode()
            } else {
              that.$message.error(response.data.message)
            }
          }).catch(function (error) {
            console.log(error)
          })
      },

      getTestPaperForMode () {
        let that = this
        that.axios.get(that.$http.httpUrl('/api/pagination'), {
          params: {
            sqlId: 'mode.paper.info.list',
            modeId: that.queryParams.id,
            currentPage: that.currentPage,
            offset: (that.currentPage - 1) * that.pageSize,
            limit: that.pageSize
          }
        }).then(function (response) {
          that.testPaperListForMode = response.data.data
          that.totalCount = response.data.total
        }).catch(function (error) {
          console.log(error)
        })
      }
    },

    filters: {
      getGradeName (gradeType) {
        for (let i = 0; i < thisPage.gradeTypeList.length; i++) {
          if (thisPage.gradeTypeList[i].code === gradeType) {
            return thisPage.gradeTypeList[i].value
          }
        }
      }
    }
  }
</script>

<style scoped>

</style>
