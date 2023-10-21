<template>
  <div>
    <template v-for="list in menuList" >
      <el-submenu
        v-if="list.children && list.children.length > 0"
        :key="list.value"
        :index="list.value + ''"  >
        <template slot="title">
          <span>{{ list.label}}</span>
        </template>
       <treeMenu @clickMenu="changeMenu" :menuList="list.children"></treeMenu>
      </el-submenu>
      <el-menu-item
        v-else
        @click="selectMenu(list)"
        :index="list.value + ''"
        :key="list.value">
        <span> {{list.label}}</span>
      </el-menu-item>
    </template>
  </div>
</template>

<script>
  export default{
    name: 'treeMenu',
    props: {
      menuList: {
        type: Array,
        required: true,
        default () {
          return []
        }
      }
    },

    data () {
      return {

      }
    },

    methods: {
      selectMenu (item) {
       this.$emit('clickMenu', item)
      },

      changeMenu (item) {
        this.$emit('clickMenu', item)
      }

    }
  }
</script>

<style>
  .el-menu-item.is-active {
    background-color: #fff !important;
  }
</style>
