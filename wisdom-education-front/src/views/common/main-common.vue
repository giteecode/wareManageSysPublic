<template>
  <section class="main" :style="{minHeight:minHeight+'px'}">
    <transition name="fade-transform" mode="out-in">
      <router-view :key="key"/>
    </transition>
  </section>
</template>

<script>
    export default {
      name: 'main-common',
      data() {
        return {
          minHeight:0
        }
      },
    mounted(){
      this.minHeight = document.documentElement.clientHeight - 160
      let that = this
      window.onresize = function() {
        that.minHeight = document.documentElement.clientHeight - 160
      }
    },
    computed: {
        key () {
          return this.$route.fullPath
        }
      }
  }
</script>

<style scoped>

</style>
