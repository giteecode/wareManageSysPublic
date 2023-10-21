<template>
	<view>
		<view class="header">
			<view class="bg">
				<view class="box">
					<view class="box-hd">
						<view class="avator">
							<img v-if="!studentInfo.headImg" src="/static/user/face.jpg">
							<img v-else :src="studentInfo.headImg">
						</view>
						<view class="phone-number">{{studentInfo.name}}</view> 
					</view>
					<view class="box-bd">
						<view class="item" @click="goMessage">
							<view class="icon">
								<img src="../../../static/image/message.png">
							</view>
							<view class="text">我的通知</view>
						</view>
					<!-- 	<view class="item">
							<view class="icon"><img src="/static/user/favorite.png"></view>
							<view class="text">我的收藏</view>
						</view> -->
						<view class="item" @click="goWrongBook">
							<view class="icon"><img src="/static/user/service.png"></view>
							<view class="text">我的错题本</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="list-content">
			<view class="list" @click="goUserInfo">
				<view class="li noborder" >
					<view class="icon"><image src="/static/user/card.png"></image></view>
					<view class="text">个人资料</view>
					<image class="to" src="/static/user/to.png"></image>
				</view>
			</view>
			<view class="list">
				<view class="li " @click="goExamHistory">
					<view class="icon"><image src="/static/user/exam-history.png"></image></view>
					<view class="text">考试记录</view>
					<image class="to" src="/static/user/to.png"></image>
				</view>
				<!-- <view class="li " >
					<view class="icon"><image src="/static/user/help.png"></image></view>
					<view class="text">帮助中心</view>
					<image class="to" src="/static/user/to.png"></image>
				</view> -->
				<view class="li" @click="goAbout">
					<view class="icon"><image src="/static/user/about.png"></image></view>
					<view class="text">关于我们</view>
					<image class="to" src="/static/user/to.png"></image>
				</view>
				<!-- <view class="li " >
					<view class="icon"><image src="/static/user/opinion.png"></image></view>
					<view class="text">意见反馈</view>
					<image class="to" src="/static/user/to.png"></image>
				</view> -->
				
				<view class="li" @click="goSetting" >
					<view class="icon"><image src="/static/user/set.png"></image></view>
					<view class="text">系统设置</view>
					<image class="to" src="/static/user/to.png"></image>
				</view>
			</view>
			
			<view @click="logout" class="list">
				<view class="li noborder" >
					<view class="icon"><image src="/static/user/exit.png"></image></view>
					<view class="text">退出登录</view>
					
				</view>
			</view>
			
			<uni-popup 
			  id="logoutDialog" 
			  ref="logoutDialog" 
			  type="dialog">
			
				<uni-popup-dialog 
				  :type="msgType"
				  content="确定退出系统吗？" 
				  :before-close="true"
				  @confirm="dialogConfirm" 
				  @close="dialogClose"></uni-popup-dialog>
			</uni-popup>
		</view>
	</view>
</template>
<script>
	import httpApi from '@/api/http'
	import uniPopupDialog from '@/components/uni-popup-dialog/uni-popup-dialog.vue'
	import uniPopup  from '@/components/uni-popup/uni-popup.vue'
	import { mapGetters } from 'vuex'
	export default {
	    components: {uniPopupDialog, uniPopup},
		data() {
			return {
				msgType: 'wran'
			}
		},
		
		computed: {
			...mapGetters({
				studentInfo: 'user/getStudentInfo',
				fileHost: 'common/fileHost'
			})
		},
		
		onLoad() {
			
		},
		
		methods: {
			changeSkin(){
				uni.navigateTo({
					url: './skin-change'
				});
			},
			goUserInfo () {
				uni.navigateTo({
					url: './userInfo'
				})
			},
			
			goWrongBook () {
				
				httpApi.get('/student/wrongBook/questionNumber')
				    .then(res => {
				      let result = res.data
					  if (result === 0) {
						 uni.showToast({
						 	icon: 'none',
						 	title: '恭喜您暂时没有错题'
						 }); 
					  } else {
						  uni.navigateTo({
						  	url: './wrongBook'
						  })
					  }
				    })
			},
			
			goExamHistory () {
				uni.navigateTo({
					url: './exam-history'
				})
			},
			
			goSetting () {
				uni.navigateTo({
					url: './setting'
				})
			},
			
			goMessage () {
				uni.navigateTo({
					url: './message'
				})
			},
			
			goAbout () {
				uni.navigateTo({
					url: './about'
				})
			},
			
			logout () {
				this.$refs.logoutDialog.open()
			},
			
			dialogConfirm (done) {
				uni.clearStorage()
				setTimeout(() => {
					uni.clearStorage()
					uni.redirectTo({
					    url: './login'
					})
					done()
				}, 500)
				
			},
			
			dialogClose (done) {
				done()
			}
		}
	}
</script>

<style lang="scss">
	page{
		background-color:#f1f1f1;
		font-size: 30upx;
	}
	.header{
		background: #fff;
		height: 390upx;
		padding-bottom: 110upx;
		.bg{
			width: 100%;
			height:200upx;
			padding-top:100upx;
			background-color:#4191ea;
		}
	}
	.box{
		width: 650upx;
		height: 280upx;
		border-radius: 20upx;
		margin: 0 auto;
		background: #fff;
		box-shadow: 0 5upx 20upx 0upx rgba(0, 0, 150, .2); 
		.box-hd{
			display: flex;
			flex-wrap: wrap;
			flex-direction: row;
			justify-content: center;
			.avator{
				width: 160upx;
				height: 160upx;
				background: #fff;
				border: 5upx solid #fff;
				border-radius: 50%;
				margin-top: -80upx;
				overflow: hidden;
				img{
					width: 100%;
					height: 100%;
				}
			}
			.phone-number{
				width: 100%;
				text-align: center;
			}
		}
		.box-bd{
			display: flex;
			flex-wrap: nowrap;
			flex-direction: row;
			justify-content: center;
			.item{
				flex: 1 1 auto;
				display: flex;
				flex-wrap: wrap;
				flex-direction: row;
				justify-content: center;
				border-right: 1px solid #f1f1f1;
				margin: 15upx 0;
				&:last-child{
					border: none;
				}
				.icon{
					width: 60upx;
					height: 60upx;
					img{
						width: 100%;
						height: 100%;
					}
				}
				.text{
					width: 100%;
					text-align: center;
					margin-top: 10upx;
				}
			}
		}
	}
	.list-content{
		background: #fff;
	}
	.list{
		width:100%;
		border-bottom:15upx solid  #f1f1f1;
		background: #fff;
		&:last-child{
			border: none;
		}
		.li{
			width:92%;
			height:100upx;
			padding:0 4%;
			border-bottom:1px solid rgb(243,243,243);
			display:flex;
			align-items:center;
		&.noborder{
			border-bottom:0
			}
			.icon{
				flex-shrink:0;
				width:50upx;
				height:50upx;
				image{
					width:50upx;
					height:50upx;
				}
			}
			.text{
				padding-left:20upx;
				width:100%;
				color:#666;
			}
			.to{
				flex-shrink:0;
				width:40upx;
				height:40upx;
			}
		}
	}
</style>
