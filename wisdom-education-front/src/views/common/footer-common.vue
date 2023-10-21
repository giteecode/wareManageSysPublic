<template>
  <div class="footer">
    <div class="container">
      <div>
        <el-link type="success" href="http://127.0.0.1:8002" target="_blank"
          >管理后台</el-link
        >
        <el-link type="warning" href="https://element.eleme.io" target="_blank"
          >Element Ui</el-link
        >
        <el-link type="danger" href="https://cn.vuejs.org">Vue</el-link>
        <el-link type="danger">反馈建议</el-link>
        <el-link type="success">关于我们</el-link>
      </div>
      <p></p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'footer-common',
}
</script>

<style type="less" scoped>
.el-link {
  margin-left: 10px;
  font-size: 18px;
}
.footer {
  background-color: #333;
  text-align: center;
  color: #fff;
  padding: 25px 0;
  div {
    font-size: 18px;
  }
  p {
    font-size: 22px;
    font-weight: 200;
  }
}
</style>
