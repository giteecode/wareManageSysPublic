<template>
    <div class="user_content">
      <div>
        <template v-if="hasData">
          <div v-for="(message, index) in messageList" class="title" :key="index">
            <span class="el-icon-message-solid">{{message.content}}</span>

            <span style="float: right">{{message.createDate}}</span>
            <el-divider></el-divider>
          </div>
        </template>

        <template v-else>
          <el-empty description="暂无消息记录"></el-empty>
        </template>

      </div>

      <el-pagination
        v-if="total > pageSize"
        background
        @current-change="handleCurrentChange"
        :current-page="pageNumber"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next, jumper">
      </el-pagination>
    </div>
</template>

<script>
    export default {
      name: 'userInfo',
      data () {
        return {
          hasData: true,
          messageList: [],
          total: 0,
          pageSize: 5,
          pageNumber: 1
        }
      },

      beforeCreate () {
        sessionStorage.setItem('userMenuIndex', '1-3')
      },

      mounted() {

        this.getMessageList();

      },

      methods: {

        getMessageList () {
          this.axios.get(this.$httpApi.httpUrl('/student/message'), {
            params: {
              pageNumber: this.pageNumber,
              pageSize: this.pageSize
            }
          }).then(response => {
            this.messageList = response.data.data.dataList
            this.total = response.data.data.total
            if (this.total === 0) {
              this.hasData = false
            }
          })
        }
      },

      handleCurrentChange (val) {
        this.pageNumber = val
        this.getMessageList()
      }
    }
</script>

<style scoped>
  .user_content {
    margin-left: 5%;
  }
  .title {
    color: #8c939d;
  }
</style>
