<template>
	<view>
		<uni-list>
			<uni-list-item 
			 v-for="(message, index) in messaeList"
			 :key="index"
			 :title="message.content" 
			 showType="text"
			 :rightText="message.createDate" > 
				<view slot="header" class="slot-box">
					<image class="slot-image" 
					src="../../../static/image/message.png" 
					mode="widthFix"></image>
				</view>
			</uni-list-item>
		</uni-list>
		<uni-load-more :status="status"  :icon-size="16" :content-text="contentText" />
	</view>
</template>

<script>
	import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
	export default {
		components: {uniLoadMore},
		data() {
			return {
				status: 'loading',
				contentText: {
					contentdown: '上拉加载更多',
					contentrefresh: '数据加载中',
					contentnomore: '亲, 没有更多数据啦'
				},
				messaeList: [],
				currentPage: 1,
				pageSize: 10,
				extraIcon: {
					color: '#4cd964',
					size: '22',
					type: 'gear-filled'
				}
			};
		},
		
		onPullDownRefresh() {
			this.currentPage = 1
			this.messaeList = []
			this.status = 'loading'
			setTimeout(() => {
				this.getMessageList()
			}, 300)
			
		},
		
		onReachBottom() {
			this.status = 'more' 
			this.currentPage++
			this.getMessageList()
		},
		
		onLoad () {
			this.getMessageList()
		},
		
		methods: {
			getMessageList () {
				let params = {
					pageNumber: this.currentPage,
					pageSize: this.pageSize
				}
				this.$httpApi.get('/student/message', params)
					.then(res => {
						let result = res.data
						if (result.dataList.length === 0 || result.dataList.length <= this.pageSize) {
							this.status = 'noMore'
							this.messaeList = this.messaeList.concat(result.dataList)
						} else {
							this.messaeList = this.messaeList.concat(result.dataList)
						}
						uni.stopPullDownRefresh()
					})
			},
			onClick(e) {
				console.log('执行click事件', e.data)
				uni.showToast({
					title: '点击反馈'
				});
			},
			switchChange(e) {
				uni.showToast({
					title: 'change:' + e.value,
					icon: 'none'
				});
			}
		}
	};
</script>

<style>
	@charset "UTF-8";

	/* 头条小程序组件内不能引入字体 */
	/* #ifdef MP-TOUTIAO */
	@font-face {
		font-family: uniicons;
		font-weight: normal;
		font-style: normal;
		src: url("~@/static/uni.ttf") format("truetype");
	}

	/* #endif */
	/* #ifndef APP-NVUE */
	page {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		background-color: #efeff4;
		min-height: 100%;
		height: auto;
	}

	view {
		font-size: 14px;
		line-height: inherit;
	}

	.example {
		padding: 0 15px 15px;
	}

	.example-info {
		padding: 15px;
		color: #3b4144;
		background: #ffffff;
	}

	.example-body {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
		padding: 0;
		font-size: 14px;
		background-color: #ffffff;
	}

	/* #endif */
	.example {
		padding: 0 15px;
	}

	.example-info {
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
		padding: 15px;
		color: #3b4144;
		background-color: #ffffff;
		font-size: 14px;
		line-height: 20px;
	}

	.example-info-text {
		font-size: 14px;
		line-height: 20px;
		color: #3b4144;
	}

	.example-body {
		flex-direction: column;
		padding: 15px;
		background-color: #ffffff;
	}

	.word-btn-white {
		font-size: 18px;
		color: #FFFFFF;
	}

	.word-btn {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		justify-content: center;
		border-radius: 6px;
		height: 48px;
		margin: 15px;
		background-color: #007AFF;
	}

	.word-btn--hover {
		background-color: #4ca2ff;
	}

	.slot-box {
		/* #ifndef APP-NVUE */
		/* display: flex; */
		/* #endif */
		flex-direction: row;
		align-items: center;
	}

	.slot-image {
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
		margin-right: 10px;
		width: 30px;
		height: 30px;
	}

	.slot-text {
		flex: 1;
		font-size: 14px;
		color: #4cd964;
		margin-right: 10px;
	}
</style>