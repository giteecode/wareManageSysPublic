<template>
	<view class="content">
		<!-- <view class="html-style" v-html="getContent"></view> -->
		<text class="title">智慧云教育平台服务使用协议</text>
		<rich-text class="html-style" :nodes="getContent"></rich-text>
	</view>
</template>

<script>
	// import I18n from '@/common/i18n.js';
	export default {
		data() {
			return {
				
			}
		},
		computed: {
			getContent() {
				return "测试中"
			}
		},
		methods: {
			
		}
	}
</script>

<style>
	.content {
		padding: 20rpx;
	}
	.title {
		position: relative;
		width: 300rpx;
		left: 50%;
		margin-left: -170rpx;
		margin-top: 20rpx;
		margon-bottom: 20rpx;
		font-weight: bold;
		text-align: center;		
	}
	.html-style {
		font-size: 32rpx;
	}
</style>
