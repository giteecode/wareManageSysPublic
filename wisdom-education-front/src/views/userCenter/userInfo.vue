<template>
  <div class="user_content">
    <el-form
      :model="userInfo"
      :rules="rules"
      ref="userInfoForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="个人头像">
        <div v-if="headImg">
          <ul
            class="el-upload-list el-upload-list--picture-card"
            style="display: inline-block;"
          >
            <li class="el-upload-list__item is-success">
              <img
                :src="headImg"
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
                <!--<span class="el-upload-list__item-preview"><i
                          class="el-icon-zoom-in"></i></span>-->
                <span class="el-upload-list__item-delete" @click="deleteHeadImg"
                  ><i class="el-icon-delete"></i
                ></span>
              </span>
              <el-dialog
                :visible.sync="headImageVisible"
                custom-class="image-dialog"
                append-to-body
              >
                <img width="100%" :src="fileHost + headImg" alt="" />
              </el-dialog>
            </li>
          </ul>
        </div>
        <div v-else>
          <el-upload
            :action="uploadAction"
            :with-credentials="true"
            :headers="headers"
            class="avatar-uploader"
            :on-success="uploadHeadImgSuccess"
            :before-upload="beforeUploadHeadImg"
            :show-file-list="false"
          >
            <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
      </el-form-item>

      <el-form-item label="姓名" prop="name">
        <el-input v-model="userInfo.name"></el-input>
      </el-form-item>

      <el-form-item label="性别" prop="sex">
        <el-select
          v-model="userInfo.sex"
          placeholder="请选择"
          @change="changeSex"
        >
          <el-option
            v-for="item in sexType"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <!-- <el-form-item label="年龄" prop="age">
          <el-input-number v-model="userInfo.age" :min="1" :max="20" label="请输入年龄"></el-input-number>
        </el-form-item>
-->
      <el-form-item label="年级">
        <el-input v-model="userInfo.gradeInfoName" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="联系方式" prop="mobile">
        <el-input v-model="userInfo.mobile"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="updateInfo" type="primary">立即修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "userInfo",
  data() {
    return {
      headers: {
        Authorization: this.$store.state.user.token,
        Platform: "educationStudent"
      },
      sexType: [
        {
          value: 1,
          label: "男"
        },
        {
          value: 2,
          label: "女"
        }
      ],
      headImg: "",
      rules: {
        name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        age: [{ required: true, message: "请输入年龄", trigger: "change" }],
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        mobile: [
          { required: true, message: "请输入联系方式", trigger: "change" }
        ]
      },
      headImageVisible: false,
      uploadAction: this.$httpApi.httpUrl("/api/upload/2"),
      fileHost: this.$store.state.common.fileHost
    };
  },

  computed: {
    ...mapGetters({
      userInfo: "user/getUserInfo"
    })
  },

  created() {},

  mounted() {
    this.headImg = this.userInfo.headImg;
  },

  methods: {
    changeSex() {
      //强制渲染
      this.$forceUpdate();
    },
    uploadHeadImgSuccess(res) {
      if (res.code === 1) {
        this.userInfo.headImg = res.url;
        this.headImg = this.userInfo.headImg;
      } else {
        this.$message.error(res.message);
      }
    },

    deleteHeadImg() {
      this.$confirm("确定移除头像吗？").then(() => {
        this.userInfo.headImg = "";
        this.headImg = "";
      });
    },

    updateInfo() {
      this.$refs["userInfoForm"].validate(valid => {
        if (valid) {
          this.axios
            .post(this.$httpApi.httpUrl("/student/info"), this.userInfo)
            .then(response => {
              this.$store.commit("user/updateUserInfo", this.userInfo);
              this.$message.success(response.data.message);
            });
        } else {
          return false;
        }
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
      let isLimit = file.size / 1024 / 1024 < 1;
      if (!isLimit) {
        this.$message.error("上传图片大小不能超过 1MB!");
        return false;
      }
      return true;
    }
  }
};
</script>

<style scoped>
.user_content {
  margin-left: 5%;
}

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
</style>
