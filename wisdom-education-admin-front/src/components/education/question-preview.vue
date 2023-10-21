<template>
    <div>
      <el-dialog title="试题预览" @closed="closeQuestionDialog" :visible.sync="dialogFormVisible">

        <div class="content" v-html="questionInfoData.content"></div>

        <div v-if="questionInfoData.questionType === 1">
          <div v-for="(item, index) in questionInfoData.optionList" :key="index" class="radio_item">
            <el-radio :label="item.label">
              {{item.label}}. &nbsp;
              <label v-html="item.option_name"></label>
            </el-radio>
          </div>
        </div>

        <div v-if="questionInfoData.questionType === 6">
          <div class="radio_item">
            <el-radio label="1">对</el-radio>
          </div>
          <div class="radio_item">
            <el-radio label="1">错</el-radio>
          </div>
        </div>

        <div v-if="questionInfoData.questionType === 2">
          <div v-for="(item, index) in questionInfoData.optionList" :key="index" class="radio_item">
            <el-checkbox :label="item.label">
              {{item.label}}. &nbsp;
              <label v-html="item.option_name"></label>
            </el-checkbox>
          </div>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="close()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
    export default {
      name: 'question-preview',
      props: {
        openFlag: {
          type: Boolean,
          required: false
        },

        questionInfo: {
          type: Object,
          required: true
        }
      },

      watch: {
        openFlag (val) {
          this.dialogFormVisible = val
        },

        questionInfo (val) {
          this.questionInfoData = val
          let options = this.questionInfoData.options
          if (options) {
            let optionList = JSON.parse(options)
            this.questionInfoData.optionList = optionList
          }
        }
      },

      data () {
        return {
          dialogFormVisible: false,
          questionInfoData: {}
        }
      },

      mounted () {
        this.questionInfoData = this.questionInfo
        this.dialogFormVisible = this.openFlag
      },

      methods: {

        close () {
          this.dialogFormVisible = false
          this.$emit('closeQuestionPreview')
        },

        closeQuestionDialog () {
          this.dialogFormVisible = false
          this.$emit('closeQuestionPreview')
        }
      }
    }
</script>
<style>
  .radio_item p {
    display: inline-block;
  }
</style>
<style scoped>
  .radio_item {
    margin: 20px;
  }


</style>
