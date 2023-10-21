<template>
  <div>
    <el-dialog
      @closed="closeDictValueDialog"
      :title="!form.id ? '新增字典值' : '修改字典值'"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible">
      <el-form :model="form" :rules="rules" ref="form" @keyup.enter.native="saveOrUpdate()" label-width="120px">
        <el-form-item label="字典值描述" prop="value">
          <el-input v-model="form.value"></el-input>
        </el-form-item>

        <el-form-item label="字典值" prop="code">
          <el-input v-model="form.code"></el-input>
        </el-form-item>

        <el-form-item label="排序">
          <el-input v-model="form.sort"></el-input>
        </el-form-item>

       <!-- <el-form-item label="所属父类">
          <el-cascader
            clearable
            :options="parentDictValueList"
            v-model="form.parentArrayId"
            change-on-select>
          </el-cascader>
        </el-form-item>-->
      </el-form>

      <span slot="footer" class="dialog-footer">
      <el-button @click="closeForm">取消</el-button>
      <el-button type="primary" @click="saveOrUpdate()">确定</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<script>
  import {isInt} from '@/utils/validate'
  export default {
    name: 'dict-value-form',
    props: {
      system_dict_id: {
        type: Number,
        required: true
      },

      showFlag: {
        type: Boolean,
        required: false,
        default: false
      },

      formData: {
        type: Object,
        required: false,
        default: {

        }
      }
    },

    watch: {
      formData (val) {
        this.form = val
      },

      system_dict_id (val) {
        this.systemDictId = val
      },

      showFlag (val) {
        this.dialogVisible = val
        if (this.dialogVisible) {
          this.getTreeDictValueList()
        }
      }
    },

    mounted () {
      this.form = this.formData
      this.dialogVisible = this.showFlag
      this.systemDictId = this.system_dict_id
    },

    data () {
      let validateCode = (rule, value, callback) => {
        if (!value) {
          callback(new Error('请输入字典值'))
        } else {
          if (!isInt(value)) {
            // eslint-disable-next-line standard/no-callback-literal
            callback('请输入整数类型的字典值')
          }
        }
        callback()
      }
      return {
        dialogVisible: false,
        props: {
          value: 'id'
        },
        systemDictId: null,
        form: {},
        rules: {
          value: [
             {required: true, message: '请输入字典值名称', trigger: 'blur'}
          ],
          code: [
             {required: true, validator: validateCode, trigger: 'blur'}
          ]
        }
       // parentDictValueList: []
      }
    },

    methods: {
      closeForm () {
        this.dialogVisible = false
        this.$emit('close')
      },

      closeDictValueDialog () {
        this.dialogVisible = false
        this.$emit('closeDialog')
      },

      saveOrUpdate () {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.form.systemDictId = this.systemDictId
            this.axios.post(this.$http.httpUrl('/api/dictValue'), this.form)
                .then(response => {
                  this.$message({
                    type: 'success',
                    message: response.data.message
                  })
                  this.form = {}
                  this.closeForm()
                })
          }
        })
      },

      getTreeDictValueList () {
        this.axios.get(this.$http.httpUrl('/dict/getDictValueTreeList')).then((response) => {
          if (response.data.code === 1) {
            this.parentDictValueList = response.data.data
          }
        }).catch((e) => {
          console.log(e)
        })
      }
    }
  }
</script>

<style scoped>

</style>
