<template>
  <div>
    <header-common/>
    <main-common/>
    <footerCommon/>
  </div>
</template>

<script>
  import headerCommon from './common/header-common'
  import mainCommon from './common/main-common'
  import footerCommon from './common/footer-common'
  export default {
    name: 'index',
    components: {headerCommon, mainCommon, footerCommon},
    data () {
      return {

      }
    },

    mounted () {

    },

    methods: {

    }
  }
</script>

<style scoped>

</style>
