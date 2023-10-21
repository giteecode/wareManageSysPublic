<template>
	<view class="container">
		<view class="ui-all">
			<view class="avatar" @tap="avatarChoose">
				<view class="imgAvatar" >
					<view class="iavatar"
					 :style="'background: url('+avater+') no-repeat center/cover #eeeeee;'"></view>
				</view>
				<text>点击更换头像</text>
			</view>
			<view class="ui-list">
				<text>姓名</text>
				<input type="text" 
				:placeholder="value" 
				:value="studentInfo.name"
				 @input="bindnickName" placeholder-class="place" />
			</view>
			<view class="ui-list">
				<text>联系方式</text>
				<input type="text" 
				:placeholder="value" 
				:value="studentInfo.mobile" 
				@input="bindMobile" placeholder-class="place" />
			</view>
			
			<view class="ui-list right">
				<text>年级</text>
				<input type="text"
				 :value="studentInfo.gradeInfoName"
				 :disabled="true"
				  placeholder-class="place" />
			</view>
						
			<view class="ui-list right">
				<text>性别</text>
				<picker @change="bindSex"
				 mode='selector' 
				 range-key="name" 
				 :index="studentInfo.sex"
				 :value="studentInfo.sex" :range="sex">
					<view class="picker">
						{{sex[index].name}}
					</view>
				</picker>
			</view>
			<!-- <view class="ui-list right">
				<text>生日</text>
				<picker mode="date" :value="date" @change="bindDateChange">
					<view class="picker">
						{{studentInfo.birthday}}
					</view>
				</picker>
			</view> -->
			
			<button class="save" @tap="savaInfo">保 存 修 改</button>
		</view>

	</view>
</template>

<script>
	
	import { mapGetters } from 'vuex'
	export default {
		data() {
			return {
				studentInfo: {
					headImg: '',
					name: '',
					//birthday: null,
					gradeInfoName: '',
					gradeInfoId: null,
					sex: 1,
					mobile: ''
				},
				studentInfoCache: null,
				sex: [{
					id: 1,
					name: '男'
				}, {
					id: 2,
					name: '女'
				}],
				index: 1,
				avater: '',
				value: '请填写',
				date: '请填写'
			}

		},
		
		computed: {
			...mapGetters({
				fileHost: 'common/fileHost'
			})
		},
		
		mounted () {
			this.studentInfoCache = this.$store.getters['user/getStudentInfo']
			this.studentInfo = {
				name: this.studentInfoCache.name,
			    gradeInfoName: this.studentInfoCache.gradeInfoName,
				sex: this.studentInfoCache.sex,
				mobile:  this.studentInfoCache.mobile
			}
			 
			if (this.studentInfoCache.headImg) {
				this.studentInfo.headImg = this.studentInfoCache.headImg
			} else {
				this.studentInfo.headImg = '/static/user/face.jpg'
			}
			this.index = this.studentInfoCache.sex - 1
			this.avater =  this.studentInfo.headImg
		},
		
		methods: {
			bindDateChange(e) {
				this.studentInfo.birthday = e.detail.value;
			},
			bindnickName(e) {
				this.studentInfo.name = e.detail.value
			},
			
			bindSex(e) {
				this.studentInfo.sex = e.detail.value + 1
				this.index = e.detail.value
			},
						
			bindMobile(e) {
				this.studentInfo.mobile = e.detail.value
			},
			
			avatarChoose() {
				uni.chooseImage({
					count: 1,
					sizeType: ['original', 'compressed'],
					sourceType: ['album', 'camera'],
					success: (res) => {
						this.imgUpload(res.tempFilePaths);
					}
				})
			},
			
			savaInfo() {
				if (!this.studentInfo.name) {
					uni.showToast({
						title: '请填写昵称',
						icon: 'none',
						duration: 2000
					});
					return;
				}
				this.updateInfo()
			},
	
			
		    updateInfo() {
				this.$httpApi.post('/student/info', this.studentInfo)
				.then(res => {
					this.studentInfoCache = this.studentInfo
					console.log(this.studentInfoCache)
				    this.$store.commit('user/updateStudentInfo', this.studentInfoCache)
					uni.showToast({
						title: '资料修改成功'
					})
				})
			},
			
			imgUpload(file) {
				this.$httpApi.upload('/api/upload/2', file)
				.then(res => {
					this.studentInfo.headImg = this.fileHost + res.url
					this.avater = this.studentInfo.headImg
				})
			}
		},
		onLoad() {			
		}

	}
</script>

<style lang="less">
	.container {
			display: block;
			background: #FFFFFF;
		}
	
	.ui-all {
		padding: 20rpx 40rpx;

		.avatar {
			width: 100%;
			text-align: left;
			padding: 20rpx 0;
			border-bottom: solid 1px #ffffff;
			position: relative;

			.imgAvatar {
				width: 140rpx;
				height: 140rpx;
				border-radius: 50%;
				display: inline-block;
				vertical-align: middle;
				overflow: hidden;

				.iavatar {
					width: 100%;
					height: 100%;
					display: block;
				}
			}

			text {
				display: inline-block;
				vertical-align: middle;
			/*	color: #8e8e93;*/
				font-size: 28rpx;
				margin-left: 40rpx;
			}

			&:after {

				width: 20rpx;
				height: 20rpx;
				/*border-top: solid 1px #030303;
				border-right: solid 1px #030303;
				transform: rotate(45deg);
				-ms-transform: rotate(45deg);
				!* IE 9 *!
				-moz-transform: rotate(45deg);
				!* Firefox *!
				-webkit-transform: rotate(45deg);
				!* Safari 和 Chrome *!
				-o-transform: rotate(45deg);*/
				position: absolute;
				top: 85rpx;
				right: 0;
			}
		}

		.ui-list {
			width: 100%;
			text-align: left;
			padding: 20rpx 0;
			border-bottom: solid 1px #D0D0D0;
			position: relative;

			text {
				color: #4a4a4a;
				font-size: 28rpx;
				display: inline-block;
				vertical-align: middle;
				min-width: 150rpx;
			}

			input {
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
			}
			button{
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				background: none;
				margin: 0;
				padding: 0;
				&::after{
					display: none;
				}
			}
			picker {
				width: 90%;
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				position: absolute;
				top: 30rpx;
				left: 150rpx;
			}

			textarea {
				color: #030303;
				font-size: 30rpx;
				vertical-align: middle;
				height: 150rpx;
				width: 100%;
				margin-top: 50rpx;
			}

			.place {
				/*color: #999999;*/
				font-size: 28rpx;
			}
		}

		.right:after {
			content: ' ';
			width: 20rpx;
			height: 20rpx;
			border-top: solid 1px #030303;
			border-right: solid 1px #030303;
			transform: rotate(45deg);
			-ms-transform: rotate(45deg);
			/* IE 9 */
			-moz-transform: rotate(45deg);
			/* Firefox */
			-webkit-transform: rotate(45deg);
			/* Safari 和 Chrome */
			-o-transform: rotate(45deg);
			position: absolute;
			top: 40rpx;
			right: 0;
		}

		.save {
			background: rgb(0, 122, 255);
			border: none;
			color: #ffffff;
			line-height: 90rpx;
			margin-top: 40rpx;
			font-size: 30rpx;
		}
	}
</style>
