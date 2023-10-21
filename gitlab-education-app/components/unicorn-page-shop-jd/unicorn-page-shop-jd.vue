<template>
	<view>
		<view class="cu-bar bg-white search fixed" :style="[{top:CustomBar + 'px'}]"
			style="height: 100rpx;background-color: #0f1A1E;margin-left: -1px;width: 101%;margin-top: -1px;">
			
			<view class="search-form round" style="width: 80%;margin-right: 12rpx;">
				<text class="cuIcon-search" style="font-size: 38rpx;"></text>
				<input type="text" placeholder="输入搜索的关键词" confirm-type="search"></input>
				<text class="cuIcon-camera" style="padding-right: 2px;font-size: 38rpx;"></text>
			</view>
			<view class="cu-list grid col-2" style="width: 20%;background: transparent !important;">
				<view class="cu-item">
					<text class="lg cuIcon-scan" style="color: #ffffff;margin-top: 28rpx;font-size: 40rpx;"></text>
					<text style="color: #ffffff;margin-top: 0rpx;font-size: 18rpx;">扫一扫</text>
				</view>
				<view class="cu-item">
					<text class="lg cuIcon-message" style="color: #ffffff;margin-top: 28rpx;font-size: 40rpx;"></text>
					<text style="color: #ffffff;margin-top: 0rpx;font-size: 18rpx;">消息</text>
				</view>

			</view>
		</view>
		<view style="margin-top: 80rpx;">
			<view class="unicorn-header1">
				<scroll-view scroll-x class="text-white nav bg-blue" scroll-with-animation :scroll-left="scrollLeft" style="background: transparent !important;">
					<view class="cu-item" :class="index==TabCur?'unicorn-font-size cur':''" v-for="(item,index) in tabList" :key="index"
					 @tap="tabSelect" :data-id="index">
						{{item.name}}
					</view>
				</scroll-view>
				<swiper class="screen-swiper square-dot" :indicator-dots="true" :circular="true" v-if="TabCur==0" :autoplay="true"
				 interval="5000" duration="500" style="height: 120px;min-height: 100px;margin-top: 5px;">
					<swiper-item v-for="(item,index) in swiperList" :key="index" class="unicorn-border-radius-swiper">
						<image :src="item.url" mode="widthFix" v-if="item.type=='image'"></image>
						<video :src="item.url" autoplay loop muted :show-play-btn="false" :controls="false" objectFit="cover" v-if="item.type=='video'"></video>
					</swiper-item>
				</swiper>
			</view>

			<view v-if="TabCur==0">
				<view class="unicorn-header2" style="margin-top: -1px;">
					<swiper class="screen-swiper square-dot" :indicator-dots="true" :circular="false" :autoplay="false" interval="5000"
					 duration="500" style="min-height: 200rpx;background: transparent !important;height: 420rpx;">
						<swiper-item v-for="(item,index) in menuList" :key="index" class="unicorn-border-radius-swiper" style="background: transparent !important;">
							<view class="cu-list grid col-5 no-border" style="background: transparent !important;">
								<view class="cu-item" v-for="(itemsub,indexsub) in item" :key="indexsub" style="display: flex;align-items: center;justify-content: center;background: transparent !important;">
									<image mode="aspectFill" :src="itemsub.url" style="width: 100rpx;height: 100rpx;margin-bottom: 10rpx;border-radius: 50%;background: transparent !important;"></image>
									<text style="color: #ffffff;">{{itemsub.name}}</text>
								</view>
							</view>
						</swiper-item>
					</swiper>
				</view>
				<image mode="widthFix" src="https://file.quanzz1314.top/jd-shop/title3.jpg" style="background: transparent !important;width: 100%;margin-top: -1px;"></image>

				<view class="unicorn-body">

					<view class="cu-bar bg-white unicorn-br-1 unicorn-br-2">
						<view class="action" style="font-size: 37rpx;font-weight: 700;color: #000000;">我的频道</view>
						<view class="action">发现更多频道<text class="cuIcon-roundrightfill lg text-red" style="color: #F34C77;"></text></view>
					</view>
					<scroll-view class="floor-list  unicorn-br-3 unicorn-br-4" scroll-x style="background-color: #FFFFFF;margin-bottom: 24rpx;">
						<view class="scoll-wrapper">
							<view v-for="(item, index) in pindaoList" :key="index" class="floor-item" @click="navToDetailPage(item)">
								<image :src="item.url" mode="aspectFill"></image>
								<text class="title clamp" style="text-align: center;">{{item.name}}</text>
							</view>
						</view>
					</scroll-view>

					<view class="cu-bar bg-white unicorn-br-1 unicorn-br-2">
						<view class="action" style="font-size: 37rpx;font-weight: 700;color: #000000;">为你推荐</view>
					</view>
					<view class="guess-section unicorn-br-3 unicorn-br-4">
						<view v-for="(item, index) in goodsList" :key="index" class="guess-item" @click="navToDetailPage(item)">
							<view class="image-wrapper">
								<image :src="item.image" mode="aspectFill"></image>
							</view>
							<view class="bg-white" style="padding: 3px 0;padding-left: 5px;">
								<view class='cu-tag round bg-red sm' v-if="item.tag" style="background-color: #F65E2C;">{{item.tag}}</view>
								<text class="title" style="line-height: 50rpx;">{{item.title}}</text>
							</view>
							<text class="text-price price" style="color: #e54d42;padding-left: 5px;">{{item.price}}</text>
						</view>
					</view>
				</view>

			</view>

			<!-- 手机 -->
			<view v-if="TabCur==1" style="padding: 0 10px;margin-top: 8px;">
				<view class="cu-bar bg-white unicorn-br-1 unicorn-br-2">
					<view class="action" style="font-size: 37rpx;font-weight: 700;color: #000000;">品牌精选</view>
				</view>
				<view class="cu-list grid col-4 no-border unicorn-br-3 unicorn-br-4" style="padding-top: 0px;">
					<view class="cu-item" v-for="(item,index) in ppjxList" :key="index" style="display: flex;align-items: center;justify-content: center;">
						<image class="unicorn-image" mode="aspectFill" :src="item.url" style="width: 100rpx;"></image>
						<text>{{item.name}}</text>
					</view>
				</view>

				<!-- 限时抢购-->
				<view class="cu-bar bg-white unicorn-br-1 unicorn-br-2" style="margin-top: 8px;">
					<view class="action" style="font-size: 37rpx;font-weight: 700;color: #000000;">限时抢购</view>
					<view class="action">更多<text class="cuIcon-right lg"></text></view>
				</view>
				<scroll-view class="floor-list  unicorn-br-3 unicorn-br-4" scroll-x style="background-color: #FFFFFF;">
					<view class="scoll-wrapper">
						<view v-for="(item, index) in xsqgList" :key="index" class="floor-item" style="width: 185rpx;padding: 0 10px;">
							<image :src="item.url" mode="aspectFill" style="width: 185rpx;"></image>
							<text class="title clamp text-price price" style="text-align: center;color: #e54d42;font-size: 14px;">{{item.price}}</text>
							<text class="title clamp text-price" style="text-align: center;text-decoration: line-through;">{{item.oldPrice}}</text>
							<view class="pro-box">
								<view class="progress-box">
									<progress :percent="item.rate" activeColor="#fa436a" active stroke-width="6" />
								</view>
							</view>
							<text class="title clamp" style="text-align: center;">已售{{item.rate}}%</text>
						</view>
					</view>
				</scroll-view>

				<view class="cu-bar bg-white unicorn-br-1 unicorn-br-2" style="margin-top: 8px;">
					<view class="action" style="font-size: 37rpx;font-weight: 700;color: #000000;">手机配件</view>
				</view>
				<view class="cu-list grid col-5 no-border unicorn-br-3 unicorn-br-4" style="padding-top: 0px;">
					<view class="cu-item" v-for="(item,index) in sjpjList" :key="index" style="display: flex;align-items: center;justify-content: center;">
						<image class="unicorn-image" mode="aspectFill" :src="item.url" style="width: 100rpx;"></image>
						<text>{{item.name}}</text>
					</view>
				</view>

				<view class="cu-bar bg-white unicorn-br-1 unicorn-br-2" style="margin-top: 8px;">
					<view class="action" style="font-size: 37rpx;font-weight: 700;color: #000000;">手机榜</view>
					<view class="action">查看全部<text class="cuIcon-right lg"></text></view>
				</view>
				<view class="cu-list grid col-3 no-border unicorn-br-3 unicorn-br-4" style="padding-top: 0px;">
					<view class="cu-item" v-for="(item,index) in sjb1List" :key="index" style="display: flex;align-items: center;justify-content: center;">
						<image class="unicorn-image" mode="aspectFill" :src="item.url" style="width: 200rpx;height: 200rpx;"></image>
						<text class="text-price" style="color: #e54d42;">{{item.price}}</text>
					</view>
				</view>

				<view class="cu-list grid col-5 no-border unicorn-br-1 unicorn-br-2 unicorn-br-3 unicorn-br-4" style="margin-top: 8px;">
					<view class="cu-item" v-for="(item,index) in sjb2List" :key="index" style="display: flex;align-items: center;justify-content: center;">
						<image class="unicorn-image" mode="aspectFill" :src="item.url" style="width: 150rpx;height: 150rpx;"></image>
						<text>{{item.name}}</text>
					</view>
				</view>

				<view class="cu-bar bg-white unicorn-br-1 unicorn-br-2" style="margin-top: 8px;">
					<view class="action" style="font-size: 37rpx;font-weight: 700;color: #000000;">京东惠选</view>
				</view>
				<view class="guess-section unicorn-br-3 unicorn-br-4">
					<view v-for="(item, index) in jdhxList" :key="index" class="guess-item" @click="navToDetailPage(item)">
						<view class="image-wrapper">
							<image :src="item.image" mode="aspectFill"></image>
						</view>
						<view class="bg-white" style="padding: 3px 0;padding-left: 5px;">
							<view class='cu-tag round bg-red sm' v-if="item.tag" style="background-color: #F65E2C;">{{item.tag}}</view>
							<text class="title" style="line-height: 50rpx;">{{item.title}}</text>
						</view>
						<text class="text-price price" style="color: #e54d42;padding-left: 5px;">{{item.price}}</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	const unicornJson = require('./unicorn-page-shop-jd.js');
	export default {
		data() {
			return {
				swiperList: unicornJson.swiperList,
				tabList: unicornJson.tabList,
				menuList: unicornJson.menuList,
				pindaoList: unicornJson.pindaoList,
				goodsList: unicornJson.goodsList,
				ppjxList: unicornJson.ppjxList,
				sjpjList: unicornJson.sjpjList,
				sjb1List: unicornJson.sjb1List,
				sjb2List: unicornJson.sjb2List,
				xsqgList: unicornJson.xsqgList,
				xsqgList: unicornJson.xsqgList,
				TabCur: 0,
				scrollLeft: 0,
				CustomBar: this.CustomBar
			};
		},
		methods: {
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			}
		}
	}
</script>

<style lang="scss">
	.unicorn-header1 {
		background-image: url('https://file.quanzz1314.top/jd-shop/title1.jpg');
		background-repeat: no-repeat;
		background-size: 100% 100%;
		-moz-background-size: 100% 100%;
		padding: 0 10px;
	}

	.unicorn-header2 {
		background-image: url('https://file.quanzz1314.top/jd-shop/title2.jpg');
		background-repeat: no-repeat;
		background-size: 100% 100%;
		-moz-background-size: 100% 100%;
	}

	.unicorn-font-size {
		font-size: 35rpx;
		font-weight: 700;
	}

	.unicorn-border-radius-swiper {
		border-radius: 10px;
	}

	.unicorn-body {
		padding: 0rpx 15rpx;
	}

	.unicorn-image {
		width: 60rpx;
		height: 60rpx;
		margin-bottom: 10rpx;
	}

	.unicorn-br-1 {
		border-top-left-radius: 10px;
	}

	.unicorn-br-2 {
		border-top-right-radius: 10px;
	}

	.unicorn-br-3 {
		border-bottom-left-radius: 10px;
	}

	.unicorn-br-4 {
		border-bottom-right-radius: 10px;
	}

	.nav .cu-item.cur {
		border-bottom: 5rpx solid;
	}

	.floor-list {
		white-space: nowrap;
	}

	.scoll-wrapper {
		display: flex;
		align-items: flex-start;
	}

	.floor-item {
		width: 150upx;
		margin-right: 20upx;
		font-size: 26rpx;
		color: #303133;
		line-height: 1.8;

		image {
			width: 150upx;
			height: 150upx;
			border-radius: 6upx;
		}
	}

	.guess-section {
		display: flex;
		flex-wrap: wrap;
		background: #fff;

		.guess-item {
			display: flex;
			flex-direction: column;
			width: 48%;
			padding-bottom: 40upx;

			&:nth-child(2n+1) {
				margin-right: 4%;
			}
		}

		.image-wrapper {
			width: 100%;
			height: 330upx;
			border-radius: 3px;
			overflow: hidden;

			image {
				width: 100%;
				height: 100%;
				opacity: 1;
			}
		}

		.title {
			font-size: 32rpx;
			color: #303133;
			line-height: 80upx;
		}

		.price {
			font-size: 32rpx;
			color: #fa436a;
			line-height: 1;
		}
	}

	.pro-box {
		display: flex;
		align-items: center;
		margin-top: 10upx;
		font-size: 24rpx;
		padding-right: 10upx;
	}

	.progress-box {
		flex: 1;
		border-radius: 10px;
		overflow: hidden;
		margin-right: 8upx;
	}

	.clamp {
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		display: block;
	}
	
</style>
