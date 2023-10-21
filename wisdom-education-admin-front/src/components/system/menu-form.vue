<template>
  <div v-if="showFlag">
    <el-dialog
      @closed="closeMenuDialog"
      :title="!form.id ? '新增菜单' : '修改菜单'"
      :close-on-click-modal="false"
      :visible.sync="dialogVisible">
      <el-form :model="form" :rules="rules" ref="form" @keyup.enter.native="saveOrUpdate()" label-width="80px">
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>

        <el-form-item label="菜单url">
          <el-input v-model="form.url"></el-input>
        </el-form-item>

        <el-form-item label="权限标识">
          <el-input placeholder="请输入权限标识如: system:admin:list" v-model="form.permission"></el-input>
        </el-form-item>

        <el-form-item label="菜单图标" prop="icon">
          <el-row>
            <el-col :span="22">
              <el-popover
                ref="iconListPopover"
                placement="bottom-start"
                trigger="click"
                v-model="visible"
                popper-class="mod-menu__icon-popover">
                <div class="mod-menu__icon-inner">
                  <div class="mod-menu__icon-list">
                    <el-button
                      v-for="(item, index) in iconList"
                      :key="index"
                      @click="iconActiveHandle(item)"
                      :class="{ 'is-active': item === form.icon }">
                      <icon-svg :name="item"></icon-svg>
                    </el-button>
                  </div>
                </div>
              </el-popover>
              <el-input
                v-model="form.icon"
                v-popover:iconListPopover
                :readonly="true" placeholder="菜单图标名称"
                class="icon-list__input"></el-input>
            </el-col>
          </el-row>
        </el-form-item>

        <el-form-item label="类型">
          <el-radio-group v-model="form.type">
            <el-radio :label="1">目录</el-radio>
            <el-radio :label="2">菜单</el-radio>
            <el-radio :label="3">按钮</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="所属菜单">
          <el-cascader
            :options="treeMenu"
            :props="props"
            v-model="form.parentIds"
            change-on-select>
          </el-cascader>
        </el-form-item>

        <el-form-item label="排序">
          <el-input v-model="form.sort"></el-input>
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
    import Treeselect from '@riophae/vue-treeselect'
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'
    import Icon from '@/icons'
    export default {
      components: {Treeselect},
      name: 'menu-form',
      props: {

        menuList: {
          type: Array,
          required: false,
          default: []
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

        showFlag (val) {
          this.dialogVisible = val
        },

        menuList (val) {
          this.treeMenu = val
        }
      },

      mounted () {
        this.iconList = Icon.getNameList()
        this.form = this.formData
        this.dialogVisible = this.showFlag
        this.treeMenu = this.menuList
      },

      data () {
        return {
          visible: false,
          iconList: [],
          dialogVisible: '',
          props: {
            value: 'id'
          },
          treeMenu: [],
          form: {},
          rules: {
            name: [
              {required: true, message: '请输入菜单名称', trigger: 'blur'}
            ]
          }
        }
      },

      methods: {

        // 图标选中
        iconActiveHandle (iconName) {
          this.form.icon = iconName
          this.visible = false
        },

        closeForm () {
          this.dialogVisible = false
          this.$emit('close')
        },

        closeMenuDialog () {
          this.dialogVisible = false
          this.$emit('closeDialog')
        },

        saveOrUpdate () {
          let that = this
          let parentId = 0 // 取最后一位作为父类id
          let parentIdsLength = that.form.parentIds.length
          if (parentIdsLength > 0) {
            parentId = that.form.parentIds[parentIdsLength - 1]
          }
          that.$refs['form'].validate(function (valid, rules) {
            if (valid) {
              that.form.parentId = parentId
              delete (that.form.parentIds)
              that.axios.post(that.$http.httpUrl('/system/menu'), that.form)
                .then(function (response) {
                  that.formShowFlag = false
                  that.$message({
                    type: 'success',
                    message: response.data.message
                  })
                  that.form = {}
                  that.$emit('close')
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
        }
      }
    }
</script>

<style scoped>
  .mod-menu__icon-list {
    width: 500px;
  }
</style>
