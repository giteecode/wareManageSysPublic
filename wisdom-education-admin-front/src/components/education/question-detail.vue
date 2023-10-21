<template>
    <div>
      <el-dialog
        title="提示"
        :visible.sync="showQuestionVisible"
        width="30%">
        <span>试题预览</span>
        <h3>选择题</h3>
        <div>{{questionInfo.content}}</div>
      </el-dialog>
    </div>
</template>

<script>
    export default {
        name: 'question-detail',
        props: {
            questionVisible: {
                type: Boolean,
                default: false,
                required: false
            },

            questionInfo: {
                type: Object,
                required: true
            }
        },

        watch: {
            'questionVisible' (val) {
                this.showQuestionVisible = val
            }
        },

        data () {
            return {
                showQuestionVisible: false
            }
        },

        mounted () {
            this.showQuestionVisible = this.questionVisible
        },

        methods: {

        }
    }
</script>

<style scoped>

</style>
