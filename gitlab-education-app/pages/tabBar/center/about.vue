<template>
	<view class="content">
		<view class="top">
			<image class="logo" src="/static/image/logo.png"></image>
			<text class="title">智慧云K12智能教学平台</text>
		</view>
		<view class="content">
			<display-item 
			txt1="版本信息" 
			txt3="1.0.4" 
			:showRight="false">
			</display-item>
			<display-item 
			txt1="联系邮箱" 
			txt3="931708230@qq.com" 
			:showRight="false"></display-item>
			<display-item 
			txt1="公众号"
			 txt3="智慧云智能教育平台"
			  :showRight="false"></display-item>
			<display-item 
			txt1="官方QQ交流"
			 txt3="931708230" 
			 :showRight="false"></display-item>
			 
			 <display-item
			 @click="goService"
			 txt1="服务条款"
			  ></display-item>
		</view>
	</view>
</template>

<script>
	import displayItem from '@/components/display-item.vue'
	import cmdCelItem from "@/components/cmd-cell-item/cmd-cell-item.vue"
	export default {
		components: { displayItem, cmdCelItem },
		data() {
			return {
				
			}
		},
		methods: {
			goContact() {
				alert(1)
				uni.navigateTo({
					url: './contact'
				});
			},
			goService() {
				uni.navigateTo({
					url: './service'
				});
			}
		}
	}
</script>

<style>
	.content {
		flex: 1;
		background-color: #FFFFFF;
	}
	.top {
		height: 300rpx;
		position: relative;
	}
	.title {
		width: 100%;
		color: #828282;
		text-align: center;
		top: 200rpx;
		font-size: 32rpx;
		position: absolute;
	}
	.logo {
		width: 100rpx;
		height: 100rpx;
		left: 50%;
		margin-left: -50rpx;
		position: absolute;
		margin-top: 60rpx;
		border-radius: 16rpx;
		align-self: center;
	}
	.list {
		width: 100%;
	}
</style>
