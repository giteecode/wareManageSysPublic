<template>
    <view class="head">
        <view class="btn left" @tap="cancel" v-if="labelCancel">{{ labelCancel }}</view>
        <view class="btn right" @tap="save" v-if="labelConfirm">{{ labelConfirm }}</view>
    </view>
</template>

<script>
export default {
    name: 'robin-editor-header',
    props: {
        labelCancel: {
            type: String,
            default: '取消'
        },
        labelConfirm: {
            type: String,
            default: '确定'
        }
    },
    methods: {
        cancel: function() {
            this.$emit('cancel');
        },
        save: function() {
            this.$emit('save');
        }
    }
};
</script>

<style lang="scss" scoped>
.head {
    display: flex;
    justify-content: space-between;
    width: 100%;
    height: 100%;
    border-bottom: 1px #eee solid;
    // box-shadow: 1px 0 2px rgba(0, 0, 0, 0.1);
    background: #fff;
    .btn {
        display: block;
        width: 150upx;
        height: 80upx;
        line-height: 80upx;
        font-size: 30upx;
        color: #666;
        padding-left: 20upx;
        text-align: center;
        &.left {
            float: left;
        }
        &.right {
            float: right;
        }
    }
}
</style>
