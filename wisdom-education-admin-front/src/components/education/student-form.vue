<template>
  <div>
    <el-dialog
      @closed="closeFormDialog"
      :title="!form.id ? '新增学员' : '修改学员'"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible"
    >
      <el-form
        :model="form"
        :rules="rules"
        ref="form"
        @keyup.enter.native="saveOrUpdate()"
        label-width="80px"
      >
        <el-row>
          <el-col :span="24">
            <el-form-item label="学员照片" prop="head_img">
              <div v-if="form.headImg">
                <ul
                  class="el-upload-list el-upload-list--picture-card"
                  style="display: inline-block;"
                >
                  <li class="el-upload-list__item is-success">
                    <img
                      :src="form.headImg"
                      alt=""
                      class="el-upload-list__item-thumbnail"
                    />
                    <a class="el-upload-list__item-name"
                      ><i class="el-icon-document"></i
                    ></a>
                    <label class="el-upload-list__item-status-label">
                      <i class="el-icon-upload-success el-icon-check"></i
                    ></label>
                    <i class="el-icon-close"></i>
                    <i class="el-icon-close-tip">按 delete 键可删除</i>
                    <span class="el-upload-list__item-actions">
                      <span
                        class="el-upload-list__item-preview"
                        @click="lookHeadImg"
                        ><i class="el-icon-zoom-in"></i
                      ></span>
                      <span
                        class="el-upload-list__item-delete"
                        @click="deleteHeadImg"
                        ><i class="el-icon-delete"></i
                      ></span>
                    </span>
                    <el-dialog
                      :visible.sync="headImageVisible"
                      custom-class="image-dialog"
                      append-to-body
                    >
                      <img width="100%" :src="fileUrl + form.headImg" alt="" />
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
                  :show-file-list="false"
                >
                  <i class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input
                v-model="form.name"
                @blur="getAccount()"
                placeholder="请输入学员姓名"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="登录账号" prop="loginName">
              <el-input
                v-model="form.loginName"
                placeholder="请输入登录账号"
              ></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-radio-group v-model="form.sex">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="2">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row v-if="!form.id">
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input
                v-model="form.password"
                @focus="changeInputType"
                :type="passwordType"
                placeholder="请输入密码"
              ></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input
                v-model="form.confirmPassword"
                @focus="changeInputType"
                :type="passwordType"
                placeholder="请输入确认密码"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="出生日" prop="birthday">
              <el-date-picker
                v-model="form.birthday"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="所属年级" prop="gradeInfoId">
              <el-select
                clearable
                filterable
                v-model="form.gradeInfoId"
                placeholder="请选择年级"
              >
                <el-option
                  v-for="item in gradeInfoList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="是否禁用">
              <template>
                <el-radio-group v-model="form.disabledFlag">
                  <el-radio :label="0">正常</el-radio>
                  <el-radio :label="1">禁用</el-radio>
                </el-radio-group>
              </template>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="联系电话" prop="mobile">
              <el-input
                v-model="form.mobile"
                placeholder="请输入联系电话"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="家庭住址" prop="address">
              <el-input
                v-model="form.address"
                placeholder="请输入家庭住址"
              ></el-input>
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
import { isMobile } from "@/utils/validate";
import { getGradeInfoList } from "@/api/gradeInfo";
export default {
  name: "menu-form",
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
    formData(val) {
      this.form = val;
    },

    showFlag(val) {
      this.dialogVisible = val;
    }
  },

  data() {
    let validatorMobile = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入学员联系方式"));
      } else {
        if (!isMobile(value)) {
          callback(new Error("请输入正确联系方式"));
        }
        callback();
      }
    };

    let validateConfirmPassword = (rule, value, callback) => {
      if (!value) {
        callback(new Error("确认密码不能为空"));
      } else {
        if (this.form.password !== value) {
          callback(new Error("确认密码与密码输入不一致"));
        } else {
          callback();
        }
      }
    };

    // eslint-disable-next-line no-unused-vars
    let validatorAge = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请输入学员年龄"));
      } else {
        if (!Number.isInteger(value)) {
          callback(new Error("年龄必须为数字值"));
        }
      }
      callback();
    };
    return {
      flag: false,
      dialogSubjectFormVisible: false,
      passwordType: "text",
      headImageVisible: false,
      dialogVisible: false,
      uploadAction: this.$http.httpUrl("/api/upload/2"),
      form: {},
      gradeInfoList: [],
      headers: {
        Authorization: localStorage.getItem("Authorization"),
        Platform: "educationAdmin"
      },
      fileUrl: "",
      rules: {
        name: [{ required: true, message: "请输入学员姓名", trigger: "blur" }],
        login_name: [
          { required: true, message: "请输入登录账号", trigger: "blur" }
        ],
        school_id: [{ required: true, message: "请选择学校", trigger: "blur" }],
        birthday: [
          { required: true, message: "请选择学员出生日", trigger: "blur" }
        ],
        grade_type: [
          { required: true, message: "请选择所属年级", trigger: "blur" }
        ],
        sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
        mobile: [
          { required: true, validator: validatorMobile, trigger: "blur" }
        ],
        address: [
          { required: true, message: "请输入学员家庭住址", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入登录密码", trigger: "blur" }
        ],
        confirmPassword: [
          {
            required: true,
            validator: validateConfirmPassword,
            trigger: "blur"
          }
        ]
      },
      parentMenuList: []
    };
  },

  mounted() {
    this.form = this.formData;
    this.fileUrl = this.$http.getFileHost();
    this.dialogVisible = this.showFlag;

    getGradeInfoList({}).then(response => {
      this.gradeInfoList = response.data.data.dataList;
    });
  },

  methods: {
    changeInputType() {
      this.passwordType = "password";
    },

    getAccount() {
      let that = this;
      that.axios
        .get(that.$http.httpUrl("/api/getSpell"), {
          params: {
            keyWord: that.form.name
          }
        })
        .then(function(response) {
          that.form.loginName = response.data.data;
        });
    },

    uploadHeadImgSuccess(res, file) {
      if (res.code === 1) {
        this.form.headImg = res.url;
      } else {
        this.$message.error(res.message);
      }
    },

    lookHeadImg() {
      this.headImageVisible = true;
    },

    deleteHeadImg() {
      let that = this;
      that
        .$confirm("确定移除学员头像吗？")
        .then(function() {
          that.form.headImg = "";
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    beforeUploadHeadImg(file) {
      let imgContentType = [
        "application/x-png",
        "application/png",
        "image/png",
        "image/jpeg",
        "image/gif"
      ];
      if (imgContentType.indexOf(file.type) === -1) {
        this.$message.error("图片格式错误, 只能上传JPG/PNG 格式,请重新上传");
        return false;
      }
      let isLimit = file.size / 1024 / 1024 < 5;
      if (!isLimit) {
        this.$message.error("上传图片大小不能超过 5MB!");
        return false;
      }
      return true;
    },

    closeForm() {
      this.dialogVisible = false;
      this.$emit("close");
    },

    closeFormDialog() {
      this.dialogVisible = false;
      this.$emit("closeDialog");
    },

    saveOrUpdate: function() {
      let that = this;
      that.$refs["form"].validate(function(valid, rules) {
        if (valid) {
          that.axios
            .post(that.$http.httpUrl("/system/student"), that.form)
            .then(function(response) {
              if (response.data.code === 1) {
                that.dialogVisible = false;
                that.$message({
                  type: "success",
                  message: response.data.message
                });
                that.form = {};
                that.$emit("close");
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        }
      });
    }
  }
};
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
  border-color: #409eff;
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
