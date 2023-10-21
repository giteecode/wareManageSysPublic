<template>
  <div class="mod-role">
    <el-form :form="query" :inline="true" @keyup.enter.native="getRoleList()">
      <el-form-item>
        <el-input v-model="query.name" placeholder="角色名称" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" icon="el-icon-search" @click="searchRole">查询</el-button>
        <el-button v-if="hasPermission(['system:role:save'])" icon="el-icon-circle-plus-outline" type="primary" @click="addRole">新增</el-button>
        <el-button v-if="hasPermission(['system:role:batchDelete'])" icon="el-icon-delete" type="danger" @click="batchDelete()">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="loading"
      @select="checkData"
      @select-all="selectAll"
      @selection-change="selectionChangeHandle"
      style="width: 100%">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>

      <el-table-column
        prop="name"
        align="center"
        label="角色名称">
      </el-table-column>

      <el-table-column
        prop="remark"
        align="center"
        label="备注" >
      </el-table-column>

      <el-table-column
        prop="createDate"
        align="center"
        label="创建时间">
      </el-table-column>

      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button
            type="text"
            icon="el-icon-setting"
            size="small"
            v-if="hasPermission(['system:role:savePermission'])"
            @click="changePermission(scope.row.id)">权限设置</el-button>

          <el-button
            type="text"
            icon="el-icon-edit"
            size="small"
            v-if="hasPermission(['system:role:update'])"
            @click="updateRole(scope.row)">修改</el-button>

          <el-button
            type="text"
            icon="el-icon-delete"
            size="small"
            v-if="hasPermission(['system:role:deleteById'])"
            @click="deleteById(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :title="!form.id ? '新增角色' : '修改角色'"
      :close-on-click-modal="false"
      :visible.sync="visible">
      <el-form :model="form" :rules="rules" ref="form" @keyup.enter.native="saveOrUpdate()" label-width="80px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" placeholder="角色名称"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="备注"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="saveOrUpdate()">确定</el-button>
    </span>
    </el-dialog>

    <el-dialog title="权限设置" @opened="dialogOpened" @closed="dialogClosed" :visible.sync="permissionVisible">
      <el-tree
        :data="permissionList"
        show-checkbox
        node-key="id"
        ref="tree"
        :default-checked-keys="checkedMenuId"
        :props="defaultProps">
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogClosed">取 消</el-button>
        <el-button type="primary" @click="savePermission">确 定</el-button>
      </div>
    </el-dialog>

    <el-pagination
      background
      @size-change="sizeChangeHandler"
      @current-change="currentChangeHandler"
      :current-page="currentPage"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="pageSize"
      :total="totalCount"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        permissionVisible: false,
        visible: false,
        roleName: '',
        dataList: [],
        currentPage: 1,
        pageSize: 10,
        totalCount: 0,
        loading: true,
        dataListSelections: [],
        checkedMenuId: [],
        permissionList: [], // 权限列表
        form: {
          name: '',
          remark: ''
        },

        query: {
          name: ''
        },
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        rules: {
          name: [
            { required: true, message: '角色名称不能为空', trigger: 'blur' }
          ]
        }
      }
    },

    mounted () {
      this.getRoleList()
      this.getTreeMenuList()
    },

    methods: {
      searchRole () {
        this.currentPage = 1
        this.getRoleList()
      },

      addRole () {
        this.visible = true
        this.form = {
          name: '',
          remark: ''
        }
      },
      checkData (select, row) {
        if (select) {
          if (select.create_type === 1) {
            this.$message.error('您不能操作系统内置角色')
          }
        }
      },

      // 全选
      selectAll (select) {

      },

      formatterCreateDate (row, column) {
        if (!row.create_date) {
          return '--'
        }
        return this.moment(row.create_date).format('YYYY-MM-DD HH:mm')
      },

      dialogClosed () {
        this.permissionVisible = false
        this.$refs.tree.setCheckedKeys([])
      },

      dialogOpened () {
        this.$refs.tree.setCheckedKeys(this.checkedMenuId)
      },

      changePermission (roleId) { // 设置权限
        let that = this
        that.roleId = roleId
        that.axios.get(that.$http.httpUrl('/system/role/' + that.roleId + '/menuTree')).then(function (response) {
          that.permissionVisible = true
          that.checkedMenuId = response.data.data
        }).catch(function (error) {
          console.log(error)
        })
      },

      savePermission () {
        let that = this
        that.permission = []
        // 获取所有选择子节点
        let checkedNodes = that.$refs.tree.getCheckedNodes()
        for (let i = 0; i < checkedNodes.length; i++) {
          that.permission.push(checkedNodes[i].id)
        }
        // 获取所有选择父节点
        let checkedParentNodes = that.$refs.tree.getHalfCheckedKeys()
        for (let i = 0; i < checkedParentNodes.length; i++) {
          that.permission.push(checkedParentNodes[i])
        }
        let params = {
          roleId: that.roleId,
          menuIds: that.permission
        }
        that.axios.post(that.$http.httpUrl('/system/role/permission'), params).then(function (response) {
          that.permissionVisible = false
          that.$refs.tree.setCheckedKeys([]) // 重新清空选中的节点
          that.$message({
            type: 'success',
            message: response.data.message
          })
        }).catch(function (error) {
          console.log(error)
        })
      },
      // 获取数据列表
      getRoleList () {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/role'), {
          params: {
            pageNumber: that.currentPage,
            pageSize: that.pageSize,
            name: that.query.name
          }
        }).then(function (response) {
          that.loading = false
          that.dataList = response.data.data.dataList
          that.totalCount = response.data.data.total
        })
      },

      saveOrUpdate () {
        let that = this
        that.$refs['form'].validate(function (valid, rules) {
          if (valid) {
            that.axios.post(that.$http.httpUrl('/system/role'), that.form)
              .then(function (response) {
                that.visible = false
                if (response.data.code === 1) {
                  that.$message({
                    type: 'success',
                    message: response.data.message
                  })
                  that.getRoleList()
                } else {
                  that.$message({
                    type: 'error',
                    message: response.data.message
                  })
                }
                that.form = {
                  name: '',
                  remark: ''
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
      // 每页数
      sizeChangeHandler (val) {
        this.pageSize = val
        this.currentPage = 1
        this.getRoleList()
      },
      // 当前页
      currentChangeHandler (val) {
        this.currentPage = val
        this.getRoleList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },

      updateRole (item) {
        if (item.create_type === 1) {
          this.$message.error('您不能修改系统内置角色')
          return
        }
        this.visible = true
        this.form = {
          id: item.id,
          create_type: item.create_type,
          name: item.name,
          remark: item.remark
        }
      },

      deleteById (item) {
        let that = this
        that.$confirm('确定删除角色【' + item.name + '】?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          that.axios.delete(that.$http.httpUrl('/system/role/' + item.id))
            .then(function (response) {
              if (response.data.code === 1) {
                that.$message({
                  type: 'success',
                  message: '删除成功'
                })
                that.getRoleList()
              }
            })
        })
      },

      getTreeMenuList () {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/menu/tree'))
          .then(function (response) {
            that.permissionList = response.data.data
            console.log(that.permissionList)
          }).catch(function (error) {
            console.log(error)
          })
      },

      batchDelete () {
        let that = this
        that.$confirm('确定删除选中的角色?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          that.axios.delete(that.$http.httpUrl('/system/role/batchDelete'), {
            data: {
              ids: that.checkIds,
              sqlId: 'system.common.batchDeleteByIds',
              table: 'system_role'
            }
          }).then(function (response) {
            if (response.data.code === 1) {
              that.$message({
                type: 'success',
                message: response.data.message
              })
              that.getRoleList()
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
