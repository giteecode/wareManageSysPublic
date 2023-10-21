<template>
  <div class="mod-user">
    <el-card class="box-card">
        <div>
          <label style="font-weight: bold; font-size: 20px">1.下载模板</label>
          <label style="margin-left: 40px; color: #a8a8a8">下载需要导入试题所用的模板</label>
        </div>

        <div style="margin: 10px">
          <el-button @click="dowLoadQuestionExcel" type="text">下载Excel模版</el-button>
          <el-button  type="text">
            <a :href="$http.httpUrl('/static/template/questionImport.txt')" download>下载TxT模版</a>
          </el-button>
        </div>

        <div>
          <label style="font-weight: bold; font-size: 20px">2.试题导入</label>
        </div>

        <div style="margin: 20px">
          <el-form :model="importForm" ref="importForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="阶段:" prop="schoolType">
              <el-select
                clearable filterable
                @change="changeSchoolType"
                v-model="importForm.schoolType"
                placeholder="请选择阶段">
                <el-option
                  v-for="item in schoolTypeList"
                  :key="item.id"
                  :label="item.value"
                  :value="item.code">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="年级:">
                <el-select
                  clearable filterable
                  @change="changeGradeInfo"
                  v-model="importForm.gradeInfoId" placeholder="请选择年级">
                  <el-option
                    v-for="item in gradeInfoList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="科目:">
              <el-select  clearable filterable v-model="importForm.subjectId" placeholder="请选择科目">
                <el-option
                  v-for="item in subjectList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="文件:">
              <el-upload
                :with-credentials="true"
                class="upload-demo"
                :data="importForm"
                :headers="headers"
                :show-file-list="false"
                :on-success="uploadExcelSuccess"
                :before-upload="beforeUploadExcel"
                :action="uploadExcelAction" >
                <el-button size="small" type="primary">导入试题</el-button>
                <div slot="tip" class="el-upload__tip">只能上传excel/txt文件</div>
              </el-upload>
            </el-form-item>
          </el-form>
        </div>
    </el-card>
  </div>
</template>
<script>
  import {getGradeInfoList} from '@/api/gradeInfo'
  import {getSubjectInfoList} from '@/api/subjectInfo'
  import {getDictValueByType} from '@/api/dict'
  export default {
    name: 'course',
    data () {
      return {
        headers: {
          Authorization: localStorage.getItem('Authorization'),
          Platform: 'educationAdmin'
        },
        gradeInfoList: [],
        subjectList: [],
        loading: null,
        importForm: {
          schoolTye: '',
          gradeInfoId: '',
          subjectId: ''
        },
        schoolTypeList: [],
        uploadExcelAction: this.$http.httpUrl('/system/question/import')
      }
    },

    watch: {

    },

    mounted () {
      getDictValueByType('school_type').then(response => {
        this.schoolTypeList = response.data.data
      })
    },

    methods: {

      changeSchoolType (val) {
        let params = {
          schoolType: val
        }
        getGradeInfoList(params).then(response => {
          this.gradeInfoList = response.data.data.dataList
        })
      },

      dowLoadQuestionExcel () {
        location.href = this.$http.httpUrl('/static/template/questionImport.xlsx')
      },

      dowLoadQuestionTxt () {
        location.href = this.$http.httpUrl('/static/template/questionImport.txt')
      },

      beforeUploadExcel () {
        if (!this.importForm.gradeInfoId) {
          this.$message.error('请选择年级')
          return false
        }

        if (!this.importForm.subjectId) {
          this.$message.error('请选择科目')
          return false
        }

        this.loading = this.$loading({
          lock: true,
          text: '数据导入中, 请等待',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })

        return true
      },

      changeGradeInfo (id) {
        let params = {
          gradeInfoId: id
        }
        getSubjectInfoList(params).then(response => {
          this.subjectList = response.data.data.dataList
        })
      },

      uploadExcelSuccess (res) {
        this.loading.close()
        if (res.code === 3) {
          this.$message.error(res.message)
          location.href = this.$http.getFileHost() + res.data
        } else if (res.code === 1) {
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
      }
    },

    filters: {

    }
  }
</script>
<style>

</style>
