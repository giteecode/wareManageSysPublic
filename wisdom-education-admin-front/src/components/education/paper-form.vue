<template>
  <div>
    <el-dialog
      @closed="closeFormDialog"
      :title="!form.id ? '新增试卷' : '修改试卷'"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible">
      <el-form :model="form" :rules="rules" ref="form" @keyup.enter.native="saveOrUpdate()" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="试卷名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入试卷名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="考试时间" prop="examTime">
              <el-input placeholder="考试时间(单位：分)" v-model="form.examTime">
                <template slot="append">分</template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row >
          <el-col :span="12">
            <el-form-item  prop="schoolType" label="所属阶段">
              <el-select
                @change="changeSchoolType"
                v-model="form.schoolType"
                filterable
                clearable
                placeholder="请输入所属阶段">
                <el-option
                  v-for="item in schoolTypeList"
                  :key="item.id"
                  :label="item.value"
                  :value="item.code">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="所属年级" prop="gradeInfoId">
              <el-select
                clearable
                filterable
                @change="changeGradeType"
                v-model="form.gradeInfoId"
                placeholder="请选择所属年级">
                <el-option
                  v-for="item in changeGradeInfoList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="科目" prop="subjectId">
              <el-select clearable filterable v-model="form.subjectId" placeholder="请选择科目">
                <el-option
                  v-for="item in changeSubjectList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="排序" prop="sort">
              <el-input type="input" placeholder="请输入整数" v-model="form.sort"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input type="textarea" placeholder="备注/注意事项" v-model="form.remark"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="closeForm">取消</el-button>
        <el-button type="primary" @click="saveOrUpdate()">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {getDictValueByType} from '@/api/dict'
  import {isInt} from '@/utils/validate'
  import {getGradeInfoList} from '@/api/gradeInfo'
  import {getSubjectInfoList} from '@/api/subjectInfo'
  export default {
    name: 'paper-form',
    props: {
      showFlag: {
        type: Boolean,
        required: false,
        default: false
      },

      formData: {
        type: Object,
        required: false,
        default: {}
      }
    },

    watch: {
      formData (val) {
        console.log(val)
        this.form = val
      },

      showFlag (val) {
        this.dialogVisible = val
      },

      'formData.schoolType' (val) {
        let params = {
          schoolType: val
        }
        getGradeInfoList(params).then(response => {
          this.changeGradeInfoList = response.data.data.dataList
        })
      },

      'formData.gradeInfoId' (val) {
        let params = {
          gradeInfoId: val
        }
        getSubjectInfoList(params).then(response => {
          this.changeSubjectList = response.data.data.dataList
        })
      }
    },

    mounted () {
      this.form = this.formData
      this.dialogVisible = this.showFlag
     // this.getSubjectList()

      getDictValueByType('school_type').then(response => {
        this.schoolTypeList = response.data.data
      })
    },

    data () {
      let validateSort = (rule, value, callback) => {
        if (value) {
          if (!isInt(value)) {
            return callback(new Error('请输入整数'))
          }
        }
        callback()
      }

      let validateExamTime = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('请输入考试时间'))
        } else {
          if (!isInt(value)) {
            return callback(new Error('考试时间必须为整数'))
          }
        }
        callback()
      }
      return {
        options: [],
        form: {},
        dialogVisible: false,
        changeGradeInfoList: [],
        changeSubjectList: [],
        schoolTypeList: [],
        rules: {
          name: [
            {required: true, message: '请输入试卷名称', trigger: 'blur'}
          ],
          examTime: [
            {required: true, validator: validateExamTime, trigger: 'blur'}
          ],
          schoolType: [
            {required: true, message: '请选择所属阶段', trigger: 'blur'}
          ],
          gradeInfoId: [
            {required: true, message: '请选择所属年级', trigger: 'blur'}
          ],
          subjectId: [
            {required: true, message: '请选择科目', trigger: 'blur'}
          ],
          sort: [
            {required: true, validator: validateSort, trigger: 'blur'}
          ]
        }
      }
    },

    methods: {
      changeGradeType (val) {
        this.form.subjectId = ''
      },

      closeFormDialog () {
        this.dialogVisible = false
        this.$emit('closeDialog')
      },

      closeForm () {
        this.dialogVisible = false
        this.$emit('closeForm')
      },

      changeSchoolType (val) {
        this.form.gradeInfoId = ''
      },

      saveOrUpdate () {
        let that = this
        that.$refs['form'].validate(function (valid, rules) {
          if (valid) {
            that.axios.post(that.$http.httpUrl('/system/testPaperInfo'), that.form)
              .then(function (response) {
                that.$message({
                  type: 'success',
                  message: response.data.message
                })
                that.dialogVisible = false
                that.form = {}
                that.$emit('close')
              }).catch(function (error) {
                console.log(error)
              })
          }
        })
      }
    }
  }
</script>

<style>
</style>
