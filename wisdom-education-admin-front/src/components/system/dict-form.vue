<template>
  <div>
    <el-dialog
      @closed="closeDictDialog"
      :title="!form.id ? '新增字典类型' : '修改字典类型'"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible">
      <el-form :model="form" :rules="rules" ref="form" @keyup.enter.native="saveOrUpdate()" label-width="80px">
        <el-form-item label="类型名称" prop="type">
          <el-input v-model="form.type"></el-input>
        </el-form-item>

        <el-form-item label="类型描述" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>


        <el-form-item label="排序">
          <el-input v-model="form.sort"></el-input>
        </el-form-item>

        <el-form-item label="备注">
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入内容"
            v-model="form.remark">
          </el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
      <el-button @click="closeForm">取消</el-button>
      <el-button type="primary" @click="saveOrUpdate()">确定</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'dict-value-form',
    props: {
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

      showFlag (val) {
        this.dialogVisible = val
      }
    },

    mounted () {
      this.form = this.formData
      this.dialogVisible = this.showFlag
    },

    data () {
      return {
        dialogVisible: false,
        props: {
          value: 'id'
        },
        form: {},
        rules: {
          type: [
            {required: true, message: '请输入类型标识', trigger: 'blur'}
          ],

          name: [
            {required: true, message: '请输入类型描述', trigger: 'blur'}
          ]
        }
      }
    },

    methods: {

      closeForm () {
        this.dialogVisible = false
        this.$emit('close')
      },

      closeDictDialog () {
        this.dialogVisible = false
        this.$emit('closeDialog')
      },

      saveOrUpdate () {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.axios.post(this.$http.httpUrl('/api/dict'), this.form)
              .then((response) => {
                if (response.data.code === 1) {
                  this.$message({
                    type: 'success',
                    message: response.data.message
                  })
                  this.form = {}
                  this.closeForm()
                } else {
                  this.$message({
                    type: 'error',
                    message: response.data.message
                  })
                }
              })
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
