<template>
  <div>
    <el-dialog
      @closed="closeFormDialog"
      :title="!form.id ? '新增广告' : '修改广告'"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible">
      <el-form :model="form" :rules="rules" ref="form" @keyup.enter.native="saveOrUpdate()" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="广告位置" prop="gradeInfoId">
              <el-select  clearable filterable v-model="form.place" placeholder="请选择广告位置">
                <el-option
                  v-for="item in placeArray"
                  :key="item.code"
                  :label="item.value"
                  :value="item.code">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="广告图片" prop="imgUrl">
              <div v-if="form.imgUrl">
                <ul class="el-upload-list el-upload-list--picture-card"
                    style="display: inline-block;">
                  <li class="el-upload-list__item is-success">
                    <img :src="fileUrl + form.imgUrl" alt=""
                         class="el-upload-list__item-thumbnail">
                    <a class="el-upload-list__item-name"><i class="el-icon-document"></i></a>
                    <label class="el-upload-list__item-status-label">
                      <i class="el-icon-upload-success el-icon-check"></i></label> <i
                    class="el-icon-close"></i>
                    <i class="el-icon-close-tip">按 delete 键可删除</i>
                    <span class="el-upload-list__item-actions">
                        <span class="el-upload-list__item-preview"  @click="lookHeadImg"><i
                          class="el-icon-zoom-in"></i></span>
                          <span class="el-upload-list__item-delete" @click="deleteHeadImg"><i
                            class="el-icon-delete"></i></span>
                        </span>
                    <el-dialog
                      :visible.sync="headImageVisible"
                      custom-class="image-dialog"
                      append-to-body>
                      <img width="100%" :src="fileUrl + form.imgUrl" alt="">
                    </el-dialog>
                  </li>
                </ul>
              </div>
              <div v-else>
                <el-upload
                  :action="uploadAction"
                  :headers="headers"
                  :with-credentials="true"
                  class="avatar-uploader"
                  :on-success="uploadHeadImgSuccess"
                  :before-upload="beforeUploadHeadImg"
                  :show-file-list="false">
                  <i class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="广告链接" prop="name">
              <el-input v-model="form.link" placeholder="请输入广告链接"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

<!--        <el-row>
          <el-col :span="24">
            <el-form-item label="平台">
              <template>
                <el-radio-group v-model="form.platform">
                  <el-radio :label="0">管理平台</el-radio>
                  <el-radio :label="1">web端</el-radio>
                  <el-radio :label="2">移动端</el-radio>
                </el-radio-group>
              </template>
            </el-form-item>
          </el-col>
        </el-row>-->
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="closeForm">取消</el-button>
        <el-button type="primary" @click="saveOrUpdate()">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {saveOrUpdate} from '@/api/advertisement'
  export default {
    name: 'advertisement-form',
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
        this.form = val
      },

      showFlag (val) {
        this.dialogVisible = val
      }
    },

    data () {
      return {
        flag: false,
        dialogSubjectFormVisible: false,
        headImageVisible: false,
        dialogVisible: false,
        uploadAction: this.$http.httpUrl('/api/upload/2'),
        form: {},
        gradeInfoList: [],
        headers: {
          Authorization: localStorage.getItem('Authorization'),
          Platform: 'educationAdmin'
        },
        placeArray: [
          {
            'code': 1,
            'value': 'web学生端首页'
          },

          {
            'code': 2,
            'value': 'h5学生端首页'
          },

          {
            'code': 3,
            'value': 'web网站logo'
          }
        ],
        fileUrl: '',
        rules: {
          imgUrl: [
            {required: true, message: '请上传广告图', trigger: 'blur'}
          ],
          place: [
            {required: true, message: '请选择广告位置', trigger: 'blur'}
          ]
        }
      }
    },

    mounted () {
      this.form = this.formData
      this.fileUrl = this.$http.getFileHost()
      this.dialogVisible = this.showFlag
    },

    methods: {
      uploadHeadImgSuccess (res, file) {
        if (res.code === 1) {
          this.form.imgUrl = res.url
        } else {
          this.$message.error(res.message)
        }
      },

      lookHeadImg () {
        this.headImageVisible = true
      },

      deleteHeadImg () {
        let that = this
        that.$confirm('确定移除该广告吗？')
          .then(function () {
            that.form.imgUrl = ''
          })
          .catch(function (error) {
            console.log(error)
          })
      },

      beforeUploadHeadImg (file) {
        let imgContentType = ['application/x-png', 'application/png', 'image/png', 'image/jpeg', 'image/gif']
        if (imgContentType.indexOf(file.type) === -1) {
          this.$message.error('图片格式错误, 只能上传JPG/PNG 格式,请重新上传')
          return false
        }
        let isLimit = file.size / 1024 / 1024 < 5
        if (!isLimit) {
          this.$message.error('上传图片大小不能超过 5MB!')
          return false
        }
        return true
      },

      closeForm () {
        this.dialogVisible = false
        this.$emit('close')
      },

      closeFormDialog () {
        this.dialogVisible = false
        this.$emit('closeDialog')
      },

      saveOrUpdate: function () {
        let that = this
        that.$refs['form'].validate(function (valid, rules) {
          if (valid) {
            saveOrUpdate(that.form).then(response => {
              if (response.data.code === 1) {
                that.dialogVisible = false
                that.$message({
                  type: 'success',
                  message: response.data.message
                })
                that.form = {}
                that.$emit('close')
              }
            })
          }
        })
      }
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
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>
