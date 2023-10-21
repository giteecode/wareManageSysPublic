<template>
	<view class="item" :class="showBottomBorder ? 'border-bottom' : ''" @click="onClick">
		<image class="icon" v-if="source != ''" :src="source"></image>
		<text class="text1" v-if="txt1 != ''">{{txt1}}</text>
		<!-- <text class="text2" v-if="txt2 != ''">{{txt2}}</text> -->
		<text class="text3" v-if="txt3 != ''">{{txt3}}</text>
		<image class="right-arrow" v-if="showRight" src="/static/images/icon/right-arrow.png"></image>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		},
		props: {
			source: {
				type: String,
				default: ''
			},
			txt1: {
				type: String,
				default: ''
			},
			txt2: {
				type: String,
				default: ''
			},
			txt3: {
				type: String,
				default: ''
			},
			showRight: {
				type: Boolean,
				default: true
			},
			showBottomBorder: {
				type: Boolean,
				default: true
			},
			onClick: {
				type: Function,
				default: ()=>{}
			}
		}
	}
</script>

<style>
	.item {
		display: flex;
		align-items: center;
		background-color: #ffffff;
		padding-top: 30rpx;
		padding-bottom: 30rpx;
		padding-left: 20rpx;
	}
	.icon {
		width: 40rpx;
		height: 40rpx;
	}
	.text1 {
		flex: 1;
		margin-left: 20rpx;
		line-height: 40rpx;
	}
	/* .text2 {		
		margin-left: 20rpx;
		line-height: 40rpx;
	} */
	.text3 {
		margin-right: 20rpx;
		line-height: 40rpx;
		color: #828282;
		font-size: 32rpx;
	}
	.border-bottom {
		border-bottom: 1rpx solid #F1F2F1;
	}
	.right-arrow {
		width: 20rpx;
		height: 20rpx;
		float: right;
		margin-right: 20rpx;
	}
</style>
