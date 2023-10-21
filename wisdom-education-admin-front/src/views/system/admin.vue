<template>
  <div class="mod-user">
    <el-form :form="query" :inline="true" @keyup.enter.native="getAdminList()">
      <el-form-item>
        <el-input v-model="query.name" placeholder="用户名/手机号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" icon="el-icon-search" v-if="hasPermission(['system:admin:list'])" @click="searchAdmin">查询</el-button>
        <el-button v-if="hasPermission(['system:admin:save'])" type="primary" @click="addAdmin">新增</el-button>
        <!--<el-button v-if="hasPermission(['system:admin:batchDelete'])" @click="getAdminList()" type="danger">批量删除</el-button>-->
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      @selection-change="selectionChangeHandle"
      v-loading="loading"
      style="width: 100%;">

      <el-table-column
        prop="loginName"
        header-align="center"
        align="center"
        width="100"
        label="账号">
      </el-table-column>

      <el-table-column
        prop="name"
        header-align="center"
        align="center"
        width="80"
        label="姓名">
      </el-table-column>

      <el-table-column
        prop="mobile"
        header-align="center"
        align="center"
        width="120"
        label="手机号">
      </el-table-column>

      <el-table-column
        prop="loginCount"
        header-align="center"
        align="center"
        width="80"
        label="登录次数">
      </el-table-column>

      <el-table-column
        prop="loginIp"
        header-align="center"
        align="center"
        min-width="100"
        label="登录ip">
      </el-table-column>

      <el-table-column
        prop="disabledFlag"
        header-align="center"
        align="center"
        width="80"
        label="状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.disabledFlag === 1" size="small" type="danger">禁用</el-tag>
          <el-tag v-else size="small">正常</el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="lastLoginTime"
        header-align="center"
        align="center"
        min-width="140"
        label="最后登录时间">
      </el-table-column>

      <el-table-column
        prop="createDate"
        header-align="center"
        align="center"
        width="150"
        label="创建时间">
      </el-table-column>

      <el-table-column
        header-align="center"
        align="center"
        min-width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button
            icon="el-icon-setting"
            size="small"
            v-if="hasPermission(['system:admin:updatePassword'])"
            @click="resettingPassword(scope.row)"
            type="text">重置密码</el-button>
          <el-button
            v-if="hasPermission(['system:admin:update'])"
             @click="updateAdmin(scope.row)"
            size="small"
             icon="el-icon-edit"
             type="text">修改</el-button>
          <el-button
            v-if="hasPermission(['system:admin:deleteById'])"
            @click="deleteAdminById(scope.row)"
            size="small"
            icon="el-icon-delete"
            type="text">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="重置密码"
      :close-on-click-modal="false"
      :visible.sync="passwordFlag">
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordForm" @keyup.enter.native="updatePassword()" label-width="80px">
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="passwordForm.newPassword" placeholder="密码"></el-input>
        </el-form-item>

        <el-form-item label="确认密码" prop="name">
          <el-input type="password" v-model="passwordForm.confirmPassword" placeholder="确认密码"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer"  class="dialog-footer">
        <el-button @click="passwordFlag = false">取消</el-button>
        <el-button type="primary" @click="updatePassword()">修改</el-button>
      </span>
    </el-dialog>

    <el-dialog
      :title="!form.id ? '新增管理员' : '修改管理员'"
      :close-on-click-modal="false"
      :visible.sync="showFlag">
      <el-form :model="form" :rules="rules" ref="form" label-width="120px">
        <el-form-item label="账号" prop="loginName">
          <el-input v-model="form.loginName" placeholder="登录帐号"></el-input>
        </el-form-item>

        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" auto-complete="off" placeholder="姓名"></el-input>
        </el-form-item>

        <template v-if="!form.id">
          <el-form-item label="密码" prop="password" :class="{ 'is-required': !form.id }">
            <el-input v-model="form.password" @focus="changeInputType" :type="passwordType" placeholder="密码"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword" :class="{ 'is-required': !form.id }">
            <el-input v-model="form.confirmPassword" :type="passwordType" placeholder="确认密码"></el-input>
          </el-form-item>
        </template>

        <el-form-item label="手机号">
          <el-input v-model="form.mobile" placeholder="手机号"></el-input>
        </el-form-item>

        <el-form-item v-if="form.superFlag === 0" label="角色" size="mini" prop="roleIds">
          <el-checkbox-group v-model="form.roleIds">
            <el-checkbox
              v-for="role in roleList"
              :key="role.id"
              :label="role.id">{{role.name}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="状态" size="mini" prop="status">
          <el-radio-group :disabled="form.superFlag === 1" v-model="form.disabledFlag">
            <el-radio :label="0">正常</el-radio>
            <el-radio :label="1">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer"  class="dialog-footer">
        <el-button @click="showFlag = false">取消</el-button>
        <el-button type="primary" @click="saveOrUpdate()">确定</el-button>
      </span>
    </el-dialog>

    <el-pagination
      background
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="currentPage"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="pageSize"
      :total="totalCount"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>

<script>
  import { isMobile } from '@/utils/validate'
  export default {
    name: 'admin',
    data () {
      let validateConfirmPassword = (rule, value, callback) => {
        if (!value) {
          callback(new Error('确认密码不能为空'))
        } else {
          if (this.form.password !== value) {
            callback(new Error('确认密码与密码输入不一致'))
          } else {
            callback()
          }
        }
      }
      return {
        currentPage: 1,
        pageSize: 10,
        totalCount: 0,
        loading: true,
        dataList: [],
        roleList: [],
        showFlag: false,
        passwordType: 'text',
        adminId: '',
        form: {
          superFlag: 0,
          loginName: '',
          name: '',
          password: '',
          confirmPassword: '',
          mobile: '',
          roleIds: [],
          disabledFlag: 0
        },
        query: {
          name: ''
        },
        checkRoleIds: [],
        passwordFlag: false,
        passwordForm: {
          newPassword: '',
          confirmPassword: ''
        },

        schoolList: [],
        passwordRules: {
          newPassword: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ],
          confirmPassword: [
            {required: true, message: '请输入确认密码', trigger: 'blur'}
          ]
        },
        rules: {
          loginName: [
            {required: true, message: '用户名不能为空', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入登录密码', trigger: 'blur'}
          ],
          confirmPassword: [
            { required: true, validator: validateConfirmPassword, trigger: 'blur' }
          ],
          roleIds: [
            { required: true, message: '请至少选择一个角色', trigger: 'blur' }
          ]
        }
      }
    },

    watch: {

    },

    mounted () {
      this.getAdminList()
      this.getRoleList()
    },

    methods: {

      // 重置密码
      resettingPassword (row) {
        this.adminId = row.id
        this.passwordFlag = true
      },

      searchAdmin () {
        this.currentPage = 1
        this.getAdminList()
      },

      addAdmin () {
        this.showFlag = true
        this.form = {
          superFlag: 0,
          loginName: '',
          name: '',
          password: '',
          confirmPassword: '',
          mobile: '',
          roleIds: [],
          disabledFlag: 0
        }
      },

      changeInputType () {
        this.passwordType = 'password'
      },

      updatePassword () {
        let that = this
        that.passwordForm.id = that.adminId
        that.$refs['passwordForm'].validate(function (valid, rules) {
          if (valid) {
            that.axios.post(that.$http.httpUrl('/system/admin/password'), that.passwordForm)
              .then(function (response) {
                that.visible = false
                if (response.data.code === 1) {
                  that.$message({
                    type: 'success',
                    message: response.data.message
                  })
                  that.passwordFlag = false
                  that.passwordForm.password = ''
                  that.passwordForm.confirmPassword = ''
                } else {
                  that.$message({
                    type: 'error',
                    message: response.data.message
                  })
                }
              }).catch(function (error) {
                console.log(error)
              })
          } else {
            let message = ''
            for (let rule in rules) {
              message = rules[rule][0].message
              if (message) {
                break
              }
            }
            that.$message({
              type: 'error',
              message: message
            })
            return false
          }
        })
      },
      // 获取数据列表
      getAdminList () {
        this.axios.get(this.$http.httpUrl('/system/admin'), {
          params: {
            pageNumber: this.currentPage,
            pageSize: this.pageSize,
            loginName: this.query.name
          }
        }).then(response => {
          this.loading = false
          let result = response.data.data
          this.dataList = result.dataList
          this.totalCount = result.total
        })
      },

      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.getAdminList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.currentPage = val
        this.getAdminList()
      },

      // 多选
      selectionChangeHandle (val) {
        this.checkedIds = val
      },

      getRoleList () {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/role')).then(function (response) {
          that.roleList = response.data.data.dataList
        })
      },

      // 表单提交
      saveOrUpdate () {
        let that = this
        if (that.form.mobile) {
          if (!isMobile(that.form.mobile)) {
            that.$message({
              type: 'error',
              message: '请输入正确手机号'
            })
            return
          }
        }
        that.save()
      },

      save () {
        let that = this
        that.$refs['form'].validate(function (valid, rules) {
          if (valid) {
            that.axios.post(that.$http.httpUrl('/system/admin'), that.form)
              .then(function (response) {
                that.visible = false
                if (response.data.code === 1) {
                  that.$message({
                    type: 'success',
                    message: response.data.message
                  })
                  that.showFlag = false
                  that.getAdminList()
                } else {
                  that.$message({
                    type: 'error',
                    message: response.data.message
                  })
                }
              }).catch(function (error) {
                console.log(error)
              })
          } else {
            let message = ''
            for (let rule in rules) {
              message = rules[rule][0].message
              if (message) {
                break
              }
            }
            that.$message({
              type: 'error',
              message: message
            })
            return false
          }
        })
      },

      // 新增 / 修改
      updateAdmin (item) {
        this.showFlag = true
        this.form = {
          id: item.id,
          loginName: item.loginName,
          name: item.name,
          mobile: item.mobile,
          disabledFlag: item.disabledFlag,
          superFlag: item.superFlag,
          roleIds: []
        }
        this.axios.get(this.$http.httpUrl('/system/admin/' + item.id))
          .then(response => {
            let systemRoleList = response.data.data.systemRoleList
            if (systemRoleList) {
              this.form.roleIds = []
              systemRoleList.forEach(item => {
                this.form.roleIds.push(item.id)
              })
            }
          })
      },

      batchDelete () {
        let that = this
        that.$confirm('确定删除选中的管理员?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          that.axios.delete(that.$http.httpUrl('/system/admin/batchDelete'), {
            data: {
              ids: that.checkIds,
              sqlId: 'system.common.batchDeleteByIds',
              table: 'system_admin'
            }
          }).then(function (response) {
            if (response.data.code === 1) {
              that.$message({
                type: 'success',
                message: response.data.message
              })
              that.getAdminList()
            } else {
              that.$message({
                type: 'error',
                message: response.data.message
              })
            }
          })
        })
      },

      // 删除
      deleteAdminById (item) {
        let that = this
        that.$confirm('确定删除管理员' + item.loginName + '?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          that.axios.delete(that.$http.httpUrl('/system/admin/' + item.id)).then(function (response) {
            if (response.data.code === 1) {
              that.$message({
                type: 'success',
                message: response.data.message
              })
              that.getAdminList()
            } else {
              that.$message({
                type: 'error',
                message: response.data.message
              })
            }
          })
        })
      }
    }
  }
</script>
