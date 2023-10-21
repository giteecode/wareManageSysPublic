<template>
	<view>
		<!-- #ifdef MP-WEIXIN -->
		<view v-for="(item, index) in videoList" :key="index" class="videoIndexFlag">
			<video :src="item.src" :id="'myVideo' + index" :muted="nowVideoMutedStatus" :show-center-play-btn="false"
			 :show-play-btn="false" :show-fullscreen-btn="false" :show-mute-btn="true" style="width: 100%;"></video>
			<view class="cu-list menu-avatar">
				<view class="cu-item v-high">
					<view class="cu-avatar round lg unicorn-primary-border" style="z-index: 2;background-color:#ffffff">
						<image mode="aspectFit" src="/static/unicorn-page-video-slide/user.png"></image>
					</view>
					<view style="z-index: 2;" class="content flex-sub">
						<view class="video-title unicorn-title">
							<text class="video-title title-cut">{{item.title}}</text>
						</view>
						<view class="text-gray text-sm">
							<span class="margin-lr-xs">{{item.auth}}</span><span>・</span>
							<span class="margin-lr-xs">播放次数{{item.playNum}}万次</span><span>・</span>
							<span class="margin-lr-xs">观看次数{{item.dayNum}}天前</span>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- #endif -->
		<!-- #ifndef MP-WEIXIN -->
		<view style="width: 100%;margin-top: 200rpx;text-align: center;padding: 0 10px;">
			<view>温馨提示</view>
			<view>当前仅支持微信小程序，其他平台敬请期待，谢谢！</view>
		</view>
		<!-- #endif -->
	</view>
</template>

<script>
	const unicornJson = require('./unicorn-page-video-slide.js');
	export default {
		data() {
			return {
				videoList: unicornJson.videoList,
				videoContextList: [],
				videoLocationInfo: [],
				scrollTopTimeOut: null,
				nowVideoMutedStatus: false,
				nowPlayVideoIndex: 0
			};
		},
		onReady: function() {
			// #ifdef MP-WEIXIN
			this.videoContextList = [];
			for (let i = 0; i < this.videoList.length; i++) {
				this.videoContextList.push(uni.createVideoContext('myVideo' + i, this));
			}
			const query = uni.createSelectorQuery().in(this);
			query.selectAll('.videoIndexFlag').boundingClientRect(data => {
				let videoLocationInfo = [];
				for (var i = 0; i < data.length; i++) {
					videoLocationInfo[i] = data[i].top;
				}
				this.videoLocationInfo = this.reCalcIndexArray(this.videoLocationInfo, videoLocationInfo);
			}).exec();

			setTimeout(() => {
				this.play(this.nowPlayVideoIndex);
			}, 200)
			// #endif
		},
		methods: {
			/**
			 * 由于当前页面是通过组件开发，unicorn-ext页面引用
			 * 组件中没有onPageScroll事件，所以通过unicorn-ext页面的onPageScroll事件调用当前组件的onPageScrollUnicorn方法
			 * 达到我们的目的
			 * 
			 * 实际开发中，可以直接定义onPageScroll事件，稍微修改使用
			 * 
			 * @param {Object} _this
			 * @param {Object} e
			 */
			onPageScrollUnicorn: function(_this, e) {
				_this.nowVideoMutedStatus = true;
				if (_this.scrollTopTimeOut != null) {
					clearTimeout(_this.scrollTopTimeOut);
				}
				_this.scrollTopTimeOut = setTimeout(() => {
					// 滑动停止的代码，此处半秒内位置不变即为滑动停止
					let nowPlayVideoIndex = _this.getIndexByArray(_this.videoLocationInfo, e.scrollTop + 100)
					//console.log(`nowPlayVideoIndex：${nowPlayVideoIndex}\nscrollTop：${scrollTop}`);
					_this.pause(_this.nowPlayVideoIndex);
					_this.nowPlayVideoIndex = nowPlayVideoIndex;
					_this.play(_this.nowPlayVideoIndex);
					_this.nowVideoMutedStatus = false;
				}, 500);
			},
			/**
			 * 重新计算Index数组信息
			 * 
			 * @param {Object} oldArray
			 * @param {Object} newArray
			 */
			reCalcIndexArray: function(oldArray, newArray) {
				if (oldArray == undefined || oldArray.length == 0) {
					//兼容第一次刷新oldArray为空的情况
					if (newArray != undefined && newArray.length > 0) {
						return newArray;
					}
					return [];
				}
				if (newArray == undefined || newArray.length == 0) {
					return oldArray;
				}
				let diff = oldArray[0] - newArray[0];
				newArray.splice(0, oldArray.length);
				for (var i = 0; i < newArray.length; i++) {
					newArray[i] = newArray[i] + diff;
				}
				//console.log("oldArray: " + JSON.stringify(oldArray));
				//console.log("newArray: " + JSON.stringify(newArray));
				return oldArray.concat(newArray);
			},
			/**
			 * 查找value在arr里面的临近值
			 * @param {Object} arr
			 * @param {Object} value
			 */
			getIndexByArray: function(arr, value) {
				//console.log(arr);
				if (!arr instanceof Array) {
					//console.error("error1");
					return -1;
				}
				if (!value) {
					//console.error("error2");
					return -1;
				}
				let value1 = Number.parseInt(value);
				return arr.findIndex(e => e - value1 > 0);
			},
			pause: function(index) {
				this.videoContextList[index].pause();
			},
			play: function(index) {
				this.videoContextList[index].play();
			},
		}
	}
</script>

<style>

</style>
