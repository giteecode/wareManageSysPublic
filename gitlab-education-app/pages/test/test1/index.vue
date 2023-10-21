<template>
	<view>
		<scroll-view scroll-x class="nav" scroll-with-animation :scroll-left="scrollLeft" style="background: transparent !important;">
			<view class="cu-item" :class="index==TabCur?'unicorn-font-size cur':''" v-for="(item,index) in tabList" :key="index"
			 @tap="tabSelect" :data-id="index">
				{{item.name}}
			</view>
		</scroll-view>
		<swiper class="screen-swiper square-dot" :indicator-dots="true" :circular="true" :autoplay="true"
		 interval="5000" duration="500" style="height: 200px;min-height: 100px;margin-top: 5px;" @change="swiperChange">
			<swiper-item v-for="(item,index) in swiperList" :key="index" style="border-radius:16upx;width:100vw;">
				<image :src="item.url" mode="widthFix" v-if="item.type=='image'" style="width:96vw;margin:2vw;border-radius:16upx;"></image>
				<video :src="item.url" autoplay loop muted :show-play-btn="false" :controls="false" objectFit="cover" v-if="item.type=='video'"></video>
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	
	export default {
		data() {
			return {
				TabCur:0,
				scrollLeft: 0,
				tabList: [{
					name: "首页"
				}, {
					name: "手机"
				}, {
					name: "电脑办公"
				}, {
					name: "食品生鲜"
				}, {
					name: "家具厨具"
				}, {
					name: "个人清护"
				}, {
					name: "男装"
				}, {
					name: "酒水饮料"
				}],
				swiperList: [{
					id: 0,
					type: 'image',
					url: '/static/banner/1.jpg'
				}, {
					id: 1,
					type: 'image',
					url: '/static/banner/2.jpg',
				}, {
					id: 2,
					type: 'image',
					url: '/static/banner/1.jpg'
				}, {
					id: 3,
					type: 'image',
					url: '/static/banner/2.jpg',
				}],
			}
		},
		onLoad() {

		},
		methods: {
			tabSelect(e) {
				this.TabCur = e.currentTarget.dataset.id;
				this.scrollLeft = (e.currentTarget.dataset.id - 1) * 60
			},
			swiperChange(e){
				this.TabCur = e.detail.current
			}

		}
	}
</script>

<style>
</style>
