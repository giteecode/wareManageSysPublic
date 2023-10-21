<template>
  <div>
    <el-form :inline="true">
      <el-form-item>
        <el-input
          v-model="query.keyWord"
          placeholder="姓名/电话号"
          clearable
          style="width: 100%"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-select
          clearable
          filterable
          v-model="query.gradeInfoId"
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

      <el-form-item>
        <el-button
          v-if="hasPermission(['system:student:list'])"
          type="primary"
          @click="searchStudent"
          icon="el-icon-search"
          >查询</el-button
        >
        <el-button
          v-if="hasPermission(['system:student:save'])"
          icon="el-icon-circle-plus"
          @click="addStudent"
          >新增</el-button
        >
      </el-form-item>

      <el-form-item v-if="hasPermission(['system:student:import'])">
        <el-upload
          :headers="headers"
          class="upload-demo"
          :before-upload="beforeUploadStudentInfo"
          :with-credentials="true"
          :on-success="uploadStudentInfoSuccess"
          :action="uploadAction"
          :show-file-list="false"
        >
          <el-button icon="el-icon-download" type="primary">导入</el-button>
        </el-upload>
      </el-form-item>

      <el-form-item>
        <el-button @click="downTemplate" type="success">下载模板</el-button>
      </el-form-item>

      <el-form-item>
        <el-button
          v-if="hasPermission(['system:studentInfo:export'])"
          @click="exportStudent"
          icon="el-icon-upload2"
          type="primary"
          plain
          >导出</el-button
        >
      </el-form-item>
    </el-form>
    <el-table
      :data="studentList"
      border
      v-loading="loading"
      style="width: 100%;"
    >
      <el-table-column prop="name" width="100" align="center" label="姓名">
      </el-table-column>

      <el-table-column align="center" label="学员头像">
        <template slot-scope="scope">
          <img
            v-if="scope.row.headImg"
            style="width: 40px; height: 40px;"
            :src="scope.row.headImg"
          />
        </template>
      </el-table-column>

      <el-table-column prop="sex" width="80" align="center" label="性别">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sex === 1" size="small">男</el-tag>
          <el-tag v-else size="small">女</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="age" width="50" align="center" label="年龄">
      </el-table-column>

      <el-table-column align="center" width="120" label="所属年级">
        <template slot-scope="scope">
          <el-tag size="small">{{ scope.row.gradeName }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="mobile"
        align="center"
        min-width="100"
        label="联系电话"
      >
      </el-table-column>

      <el-table-column
        prop="address"
        align="center"
        width="100"
        :show-overflow-tooltip="true"
        label="家庭地址"
      >
      </el-table-column>

      <el-table-column
        prop="createDate"
        align="center"
        width="140"
        label="创建时间"
      >
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        min-width="210"
        label="操作"
      >
        <template slot-scope="scope">
          <el-button
            type="text"
            icon="el-icon-loading"
            v-if="hasPermission(['system:student:updatePassword'])"
            @click="updateStudentAccount(scope.row)"
            >重置密码</el-button
          >
          <el-button
            v-if="hasPermission(['system:student:update'])"
            @click="updateStudent(scope.row)"
            icon="el-icon-edit"
            type="text"
            >修改</el-button
          >
          <el-button
            v-if="hasPermission(['system:student:deleteById'])"
            @click="deleteById(scope.row)"
            icon="el-icon-edit"
            type="text"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="修改密码"
      :visible.sync="passWordFormVisible"
      :append-to-body="true"
    >
      <el-form
        :model="passwordForm"
        :rules="dataRule"
        ref="passwordForm"
        label-width="80px"
      >
        <el-form-item label="新密码" prop="password">
          <el-input type="password" v-model="passwordForm.password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            type="password"
            v-model="passwordForm.confirmPassword"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="passWordFormVisible = false">取消</el-button>
        <el-button type="primary" @click="updateAccountPassword()"
          >确定</el-button
        >
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
      layout="total, sizes, prev, pager, next, jumper"
    >
    </el-pagination>

    <student-form
      :showFlag="formDialog"
      @closeDialog="closeDialog"
      @close="closeForm"
      :formData="studentForm"
    >
    </student-form>
  </div>
</template>
<script>
import studentForm from "@/components/education/student-form";
import { getGradeInfoList } from "@/api/gradeInfo";
let thisPage = null;
export default {
  name: "student",
  components: { studentForm },
  data() {
    let validateConfirmPassword = (rule, value, callback) => {
      if (this.passwordForm.newPassword !== value) {
        callback(new Error("确认密码与新密码不一致"));
      } else {
        callback();
      }
    };
    return {
      formDialog: false,
      studentList: [],
      gradeInfoList: [],
      fileUrl: this.$http.getFileHost(),
      query: {
        keyWord: "",
        gradeInfoId: ""
      },
      headers: {
        Authorization: localStorage.getItem("Authorization"),
        Platform: "educationAdmin"
      },
      dataRule: {
        password: [
          { required: true, message: "新密码不能为空", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          { validator: validateConfirmPassword, trigger: "blur" }
        ]
      },
      passWordFormVisible: false,
      excelLoading: false,
      studentForm: {
        name: "",
        disabledFlag: 0,
        // age: '',
        birthday: null,
        sex: "",
        loginName: "",
        password: "",
        confirmPassword: "",
        headImg: "",
        address: "",
        mobile: "",
        gradeInfoId: "",
        fatherName: "",
        motherName: ""
      },
      passwordForm: {
        password: "",
        confirmPassword: ""
      },
      loading: true,
      currentPage: 1,
      pageSize: 10,
      totalCount: 0,
      uploadAction: this.$http.httpUrl("/system/student/import")
    };
  },

  beforeCreate() {
    thisPage = this;
  },

  mounted() {
    this.getStudentList();
    getGradeInfoList({}).then(response => {
      this.gradeInfoList = response.data.data.dataList;
    });
  },

  methods: {
    updateAccountPassword() {
      this.axios
        .post(
          this.$http.httpUrl("/system/student/resetting/password"),
          this.passwordForm
        )
        .then(response => {
          this.passWordFormVisible = false;
          if (response.data.code === 1) {
            this.$message({
              type: "success",
              message: response.data.message
            });
          } else {
            this.$message({
              type: "error",
              message: response.data.message
            });
          }
        });
    },

    updateStudentAccount(item) {
      this.passwordForm.id = item.id;
      this.passwordForm.encrypt = item.encrypt;
      this.passwordForm.password = "";
      this.passwordForm.confirmPassword = "";
      this.passWordFormVisible = true;
    },

    sizeChangeHandler(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.getStudentList();
    },

    currentChangeHandler(val) {
      this.currentPage = val;
      this.getStudentList();
    },

    addStudent() {
      this.formDialog = true;
      this.clearFormData();
    },

    beforeUploadStudentInfo() {
      this.excelLoading = this.$loading({
        lock: true,
        text: "数据导入中, 请等待",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
      return true;
    },

    downTemplate() {
      //  let name = encodeURI('学生信息导入模板')
      // location.href = this.$http.getFileHost() + '/excel/' + name + '.xlsx'

      location.href = this.$http.httpUrl("/static/template/studentImport.xlsx");
    },

    uploadStudentInfoSuccess(res, file) {
      this.excelLoading.close();
      if (res.code === 1) {
        this.$message.success(res.message);
        this.getStudentList();
      } else if (res.code === 3) {
        this.$message.error(res.message);
        location.href = this.$http.getFileHost() + res.data;
      } else {
        this.$message.error(res.message);
      }
    },

    clearFormData() {
      this.studentForm = {
        name: "",
        disabledFlag: 0,
        age: "",
        sex: "",
        loginName: "",
        password: "",
        confirmPassword: "",
        headImg: "",
        address: "",
        mobile: "",
        gradeInfoId: "",
        fatherName: "",
        motherName: ""
      };
    },

    deleteById(item) {
      let that = this;
      that
        .$confirm("确定删除学员" + item.name + "?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
        .then(function() {
          that.axios
            .delete(that.$http.httpUrl("/system/student/" + item.id))
            .then(function(response) {
              if (response.data.code === 1) {
                that.$message({
                  type: "success",
                  message: response.data.message
                });
                that.getStudentList();
              }
            });
        });
    },

    // 导出学员
    exportStudent() {
      let that = this;
      let params = {
        keyWord: that.query.keyWord,
        gradeType: that.query.grade_type
      };
      that.axios
        .post(that.$http.httpUrl("/system/studentInfo/exportStudent"), params)
        .then(function(response) {
          location.href =
            that.$http.getFileHost() + "/" + response.data.message;
        });
    },

    closeForm() {
      this.formDialog = false;
      this.getStudentList();
      this.clearFormData();
    },

    closeDialog() {
      this.formDialog = false;
    },

    updateStudent(item) {
      this.studentForm = {
        id: item.id,
        disabledFlag: item.disabledFlag ? 1 : 0,
        loginName: item.loginName,
        name: item.name,
        birthday: item.birthday,
        sex: item.sex,
        headImg: item.headImg,
        address: item.address,
        mobile: item.mobile,
        gradeInfoId: item.gradeInfoId,
        fatherName: item.fatherName,
        motherName: item.motherName
      };
      this.formDialog = true;
    },

    searchStudent() {
      this.currentPage = 1;
      this.loading = true;
      this.getStudentList();
    },

    getStudentList() {
      this.axios
        .get(this.$http.httpUrl("/system/student"), {
          params: {
            pageNumber: this.currentPage,
            pageSize: this.pageSize,
            name: this.query.keyWord,
            gradeInfoId: this.query.gradeInfoId
          }
        })
        .then(response => {
          this.studentList = response.data.data.dataList;
          this.totalCount = response.data.data.total;
          this.loading = false;
        });
    }
  },

  filters: {
    getGradeName(gradeType) {
      for (let i = 0; i < thisPage.gradeTypeList.length; i++) {
        if (thisPage.gradeTypeList[i].code === gradeType) {
          return thisPage.gradeTypeList[i].value;
        }
      }
    }
  }
};
</script>
