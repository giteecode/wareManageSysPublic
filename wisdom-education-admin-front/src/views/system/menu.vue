<template>
  <div class="mod-menu">
    <el-form :inline="true" :model="menuForm">
      <el-form-item>
        <el-button @click="addMenu" v-if="hasPermission(['system:menu:save'])" type="primary">新增菜单</el-button>
      </el-form-item>
    </el-form>

    <vxe-table
      resizable
      border="inner"
      ref="xTree"
      :tree-config="{}"
      :data="menuList">
      <vxe-table-column align="center" field="label" title="名称" tree-node></vxe-table-column>
      <vxe-table-column align="center" field="icon" title="图标">
        <template slot-scope="scope">
          <icon-svg :name="scope.row.icon || ''"></icon-svg>
        </template>
      </vxe-table-column>
      <vxe-table-column align="center" field="type" title="类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 1" size="small" type="success">菜单</el-tag>
          <el-tag v-else-if="scope.row.type === 2" size="small" type="info">按钮</el-tag>
        </template>
      </vxe-table-column>

      <vxe-table-column align="center" field="parentName" title="上级菜单"></vxe-table-column>
      <vxe-table-column width="60" align="center" field="sort" title="排序"></vxe-table-column>
      <vxe-table-column align="center" field="url" title="菜单URL"></vxe-table-column>
      <vxe-table-column align="center" field="permission" title="权限标识"></vxe-table-column>
      <vxe-table-column align="center" title="操作" min-width="100">
        <template slot-scope="scope">
          <el-button
            @click="updateMenu(scope.row.id)"
            size="small"
            v-if="hasPermission(['system:menu:update'])"
            type="text" icon="el-icon-edit">修改</el-button>
          <el-button
            @click="deleteById(scope.row)"
            size="small"
            v-if="hasPermission(['system:menu:deleteById'])"
            type="text" icon="el-icon-delete">删除</el-button>
        </template>
      </vxe-table-column>
    </vxe-table>

    <menu-form
      :menuList="menuList"
      :showFlag="showFlag"
      @closeDialog="closeDialog"
      @close="closeForm"
      :formData="menuForm">
    </menu-form>
  </div>
</template>

<script>
  import menuForm from '@/components/system/menu-form'
  export default {
    components: {
      menuForm
    },
    data () {
      return {
        showFlag: false,
        loading: true,
        menuForm: {
          createType: 2,
          icon: '',
          name: '',
          url: '',
          type: 0,
          permission: '',
          parentIds: [],
          parentId: '',
          sort: 0
        },
        menuList: [],
        currentPage: 1,
        pageSize: 10,
        totalCount: 0
      }
    },

    mounted () {
      this.getMenuList()
    },

    methods: {

      addMenu () {
        this.showFlag = true
      },

      getMenuList () {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/menu/tree'))
          .then(function (response) {
            that.loading = false
            that.menuList = response.data.data
          })
      },

      closeDialog () {
        this.showFlag = false
      },

      updateMenu (id) {
        let that = this
        that.axios.get(that.$http.httpUrl('/system/menu/' + id)).then(function (response) {
          that.menuForm = response.data.data
          that.showFlag = true
        })
      },

      deleteById (item) {
        this.$confirm('确定删除菜单' + item.name + '?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.axios.delete(this.$http.httpUrl('/system/menu/' + item.id))
            .then(response => {
              this.$message({
                type: 'success',
                message: response.data.message
              })
              this.getMenuList()
            })
        }).catch(() => {

        })
      },

      closeForm () {
        this.showFlag = false
        this.getMenuList()
        this.menuForm = {
          create_type: 2,
          name: '',
          icon: '',
          url: '',
          type: 0,
          permissions: '',
          parentIds: [],
          parent_id: '',
          sort: 0
        }
      }
    }
  }
</script>

<style>
  .el-table__expand-icon {
    display: inline-block;
    margin-right: 15px;
    vertical-align:-5px;
  }

  /*.el-table [class*=el-table__row--level] .el-table__expand-icon {*/
    /*display: inline-block;*/
    /*width: 14px;*/
    /*vertical-align: middle;*/
    /*margin-right: 5px;*/
  /*}*/

  /*.el-table td div {*/
    /*box-sizing: border-box;*/
  /*}*/
  /*.el-table__expand-icon {*/
    /*position: relative;*/
    /*cursor: pointer;*/
    /*color: #666;*/
    /*transition: transform .2s ease-in-out;*/
    /*height: 20px;*/
  /*}*/
</style>
