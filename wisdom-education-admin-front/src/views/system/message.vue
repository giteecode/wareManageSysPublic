<template>
    <div class="main">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span style="font-size: 24px;font-weight: bold">消息中心</span>
        </div>
        <template v-if="hasData">
          <div v-for="(item, index) in messageList" :key="index"  class="text item">
            <span class="message-title"><i class="el-icon-bell"></i></span>
            <span class="message-content"> {{item.content}}</span>
            <span class="message-time"> {{item.create_date | formatterDate}}</span>
          </div>
        </template>

        <template v-else>
          <div style="text-align: center" class="text item">
            暂无消息通知
          </div>
        </template>

        <template v-if="totalCount > 0">
          <el-pagination
            background
            @size-change="sizeChangeHandle"
            @current-change="currentChangeHandle"
            :current-page="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="pageSize"
            layout="prev, pager, next"
            :total="totalCount">
          </el-pagination>
        </template>

      </el-card>
    </div>
</template>

<script>
    export default {
      name: 'message',
      data () {
        return {
          hasData: true,
          messageList: [],
          currentPage: 1,
          pageSize: 10,
          totalCount: 0
        }
      },

      mounted () {
        this.getMessageList()
      },

      methods: {

        sizeChangeHandle (val) {
          this.pageSize = val
          this.currentPage = 1
          this.getMessageList()
        },

        currentChangeHandle (val) {
          this.currentPage = val
          this.getMessageList()
        },

        getMessageList () {
          let that = this
          that.axios.get(this.$http.httpUrl('/system/message'), {
            params: {
              offset: (that.currentPage - 1) * that.pageSize,
              limit: that.pageSize
            }
          }).then((response) => {
            that.messageList = response.data.data.dataList
            that.totalCount = response.data.data.total
            if (that.totalCount === 0) {
              that.hasData = false
            }
          })
        }
      },

      filters: {
        formatterDate (value) {
          return this.moment(value).format('YYYY-MM-DD HH:mm')
        }
      }
    }
</script>

<style type="scss" scoped>

  .main .box-card {
    width: 100%;
    margin-top: 10px;
  }

  .text {
    line-height: 30px;
    border-bottom: 1px solid #0da9ff;
    font-size: 14px;
  }
  .text .message-title{
    position: absolute;
  }

  .text .message-content {
    width: 80%;
    padding-left: 32px;
    display: inline-block;
  }

  .text .message-time {
    float: right; color:#999; font-size: 15px
  }
  .text .message-title i {
    font-size: 24px; color: red
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }
</style>
