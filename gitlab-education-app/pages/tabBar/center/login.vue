<template>
	<view>
		<scroll-view scroll-y class="page">
			<image src="/static/loginBg1.png" mode="widthFix" class="response"></image>
			<view class="container">
				<view class="box login" :style="{'transform':loginStyle}">
					<view class="form-content">
						<view class="avtar">
							<view class="pic">
								<image src="/static/logo.png" alt="">
							</view>
						</view>
						<h1>欢迎登录</h1>
						<view>
							<view class="cu-form-group">
								<text class="cuIcon-people"></text>
								<input v-model="loginData.userName" class="margin-left-xs" placeholder="请输入账号" />
							</view>
							<view class="cu-form-group">
								<text class="cuIcon-lock"></text>
								<input v-model="loginData.password" class="margin-left-xs" type="password" placeholder="请输入密码" />
							</view>
							<view class="padding flex flex-direction">
								<button class="cu-btn bg-blue lg" @click="bindLogin">登录</button>
							</view>

							<view class="text-center">
								还没有账户? <text @click="toSign()" class="signupbtn">注册</text>
							</view>
						</view>
					</view>
				</view>
				<view class="box signup" :style="{'transform':signupStyle}">
					<view class="form-content">
						<view class="avtar">
							<view class="pic">
								<image src="/static/logo.png" alt="">
							</view>
						</view>
						<h1>欢迎加入我们</h1>
						<view>
							<view class="cu-form-group">
								<text class="cuIcon-people"></text>
								<input v-model="registerData.userName" class="margin-left-xs" placeholder="请输入账户" name="input"></input>
							</view>
							<view class="cu-form-group">
								<text class="cuIcon-phone"></text>
								<input v-model="registerData.phone" class="margin-left-xs" placeholder="请输入手机号" name="input"></input>
							</view>
							<view class="cu-form-group">
								<text class="cuIcon-lock"></text>
								<input v-model="registerData.password" class="margin-left-xs" placeholder="请输入密码" name="input"></input>
							</view>
							<view class="padding flex flex-direction">
								<button class="cu-btn bg-blue lg" @click="registe">注册</button>
							</view>

							<view class="text-center">
								已经有账号了? <text @click='tologin()' class="loginbtn">登陆</text>
							</view>
						</view>
					</view>
				</view>
			</view>

		
			<view class="other_login cuIcon">
				<view class="weixinLogin">
					<view class="line"></view>
					第三方账号登录
					<view class="line"></view>
				</view>
				<view class="login_icon_wx">
					<image style="width: 50px;height: 50px;" src="/static/wx.png" @tap="bindWeChatLogin"></image>
				</view>
				<view class="footer_des">你的教育专家</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import {mapMutations} from 'vuex';
	export default {
		data() {
			return {
				loginStyle: '',
				signupStyle: '',
				loginData:{
					userName: '',
					password:''
				},
				registerData:{
					userName: '',
					phone:'',
					password:''
				}
			}
		},

		methods: {
			...mapMutations(['login']),
			
			toSign() {
				this.loginStyle = 'rotateY(180deg)';
				this.signupStyle = 'rotateY(0deg)';
			},
			tologin() {
				this.loginStyle = 'rotateY(0deg)';
				this.signupStyle = 'rotateY(-180deg)';
			},

			bindWeChatLogin () {
				uni.showToast({
					icon: 'none',
					title: '功能暂未开放'
				});
			},

			bindLogin() {
				if (!this.loginData.userName) {
					uni.showToast({
						icon: 'none',
						title: '请输入账号'
					});
					return
				}
				if (!this.loginData.password) {
					uni.showToast({
						icon: 'none',
						title: '请输入密码'
					});
					return
				}
				uni.showLoading({
					title: '正在登录'
				});
				this.$httpApi.post('/student/login', {
					userName: this.loginData.userName,
					password: this.loginData.password
				})
				.then(res => {
					if (res.code === 1) {
						uni.hideLoading()
						uni.showToast({
							title: res.message
						})

						let studentInfo = {
							name: res.data.name,
							headImg: res.data.headImg,
							gradeInfoName: res.data.gradeInfoName,
							birthday: res.data.birthday,
							sex: res.data.sex,
							mobile: res.data.mobile,
							id: res.data.id
						}
						this.$store.commit('user/updateStudentInfo', studentInfo)
						setTimeout(() => {
							uni.switchTab({
								url: '../index/index'
							})
						}, 100)
					} else {
						uni.showToast({
							icon: 'none',
							title: res.message
						})
					}
				})
			},
			registe() {
				if (!this.registerData.userName) {
					uni.showToast({
						icon: 'none',
						title: '请输入账号'
					});
					return
				}
				if (!this.registerData.password) {
					uni.showToast({
						icon: 'none',
						title: '请输入密码'
					});
					return
				}
				if (!this.registerData.phone) {
					uni.showToast({
						icon: 'none',
						title: '请输入手机号'
					});
					return
				}
				uni.showLoading({
					title: '正在注册'
				});
				this.$httpApi.post('/student/registe', {
					userName: this.registerData.userName,
					password: this.registerData.password,
					phone: this.registerData.phone
				})
				.then(res => {
					if (res.code === 1) {
						uni.hideLoading()
						uni.showToast({
							title: res.message
						})
						setTimeout(() => {
							this.tologin();
						}, 100)
					} else {
						uni.showToast({
							icon: 'none',
							title: res.message
						})
					}
				})
			},
		}
	};
</script>

<style lang="scss">
	.page {
		height: 100vh;
		overflow: hidden;
		color:#465d63;
	}

	.container {
		position:relative;
		margin:auto;
		margin-top:-100upx;
		width: 80vw;
		perspective: 1500px;
		-webkit-perspective: 1500px;
		-moz-perspective: 1500px;
	}

	.box {
		width: 80vw;
		border-radius: 10px;
		cursor: pointer;
		backface-visibility: hidden;
		transition: all 0.5s;
		user-select: none;
	}
	.login{
		position:absolute;
	}

	.signup {
		transform: rotateY(-180deg); //解決切换闪烁问题
	}

	.container .form-content {
		width: 100%;
		height: 100%;
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		align-items: center;
		box-shadow: 0 0 1upx #3399ff;
		border-radius: 30upx;
		background-color: #fff;
		padding-bottom:60upx;
	}

	.container .form-content h1 {
		margin-top:40upx;
		color:#465d63;
	}

	.container .form-content .avtar {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		margin-top:-80upx;
	}

	.container .form-content .avtar .pic {
		position: relative;
		width: 160upx;
		height: 160upx;
		overflow: hidden;
		border-radius: 50%;
		border: 1upx solid #3399ff;
	}

	.container .form-content .avtar .pic image {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
	}
	.other_login{
		margin-top:4vh;
		width: 100vw;
		padding:0 10vw;
		display:flex;
		flex-direction: column;
	}
	.weixinLogin{
		display: flex;
		justify-content: space-evenly;
		align-items: center;
		margin-bottom:20upx;
		font-size:13px;
	}
	.line{
		border: 1upx inset #F8F8F8;
		width:25%;
		height:1upx;
	}
	.login_icon_wx{
		display:flex;
		justify-content: center;
		align-items:center;
		margin-bottom:20upx;
	}
	.footer_des{
		text-align:center;
		font-size:13px;
	}

	.loginbtn,
	.signupbtn {
		color: #0081ff;
	}
</style>
