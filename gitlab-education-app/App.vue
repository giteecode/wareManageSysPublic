<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			if (!this.hasLogin()) {
				uni.redirectTo({
				    url: '/pages/tabBar/center/login'
				})
			}
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	@import '@/common/uni.css';
	
	@import "colorui/animation.css";
	@import "colorui/main.css";
	@import "colorui/icon.css";
	
	
	/* 以下样式用于 hello uni-app 演示所需 */
	page {
	    background-color: #F4F5F6;
	    height: 100%;
	    font-size: 28upx;
	    line-height: 1.8;
	}
	
	.uni-header-logo {
	    padding: 30upx;
	    flex-direction: column;
	    justify-content: center;
	    align-items: center;
	    margin-top: 10upx;
	}
	
	.uni-header-image {
	    width: 100px;
	    height: 100px;
	}
	
	.uni-hello-text {
	    color: #7A7E83;
	}
	
	.uni-hello-addfile {
	    text-align: center;
	    line-height: 300upx;
	    background: #FFF;
	    padding: 50upx;
	    margin-top: 10px;
	    font-size: 38upx;
	    color: #808080;
	}
</style>
