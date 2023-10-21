<template>
    <view style="background-color: #FFFFFF;" class="container">

        <!-- 媒体部分 -->
        <view class="shipinpart">
            <view class="shipinpart-media">
               <view class="shipinpart-media">
                    <!-- 视频 -->
					<video class="video" id="myVideo"
							:initial-time="videoStart"
							:model="menuinfo" :src="videoSrc"
							@error="videoErrorCallback"
							@waiting="videoPlayWait"
							@timeupdate="videoTimeUpdate"
							@fullscreenchange="fullscreen"
							@play='videoOnplay'
							@pause='videoOnpause'
							@ended='videoOnend'
							:controls="videoControls"
							autoplay show-center-play-btn enable-play-gesture>
					 </video> 
                </view>
            </view>
           
        </view>



        <!-- 课程介绍、课程目录、附件、用户评价 -->
        <view class="kechengpart">
            <view class="kechengpart-title">
                <view class="kechengpart-title-item" v-for="(item, index) in kechengList" :key="index">
                    <view @click="muluchange(index)" :class="{btna:btnnum == index}">{{item}}</view>
                    <text :class="{_underline: btnnum == index}"></text>
                </view>
            </view>
            <view class="kechengpart-content">
                <!-- 课程目录 -->
                <scroll-view scroll-y="true" class="kcml" style="margin-bottom: 122upx;" :class="{dis:btnnum == 0}">
					<scroll-view v-for="(section, index) in courseSectionList" :key="index">
						<uni-section :title="'第' + (index + 1) + '章:' + section.title" type="line"></uni-section>
						<view class="kcml-list" 
						  v-for="(node, nodeIndex) in section.courseSectionNodeList"
						  :key="node.id"
						@click="playVideo(node)">
							<view class="kcml-list-left">
								<text class="shipin">视频</text>
								<text>{{index + 1}}-{{nodeIndex + 1}}&nbsp;{{node.title}}</text>
							</view>
					
						</view>
					</scroll-view>
                </scroll-view>



                <!-- 用户评价 -->
                <view class="yhpj" :class="{dis:btnnum == 1}">
                    <view class="yhpj-list" v-if="comment.length > 0">
                        <view class="item" v-for="(item, index) in comment" :key="index">
                            <image class="item-left" :src="item.avatar" mode="aspectFit"></image>
                            <view class="item-right">
                                <view class="item-right-top">
                                    <text>{{item.nickname}}</text>
                                    <text>{{item.addtime}}</text>
                                </view>
                                <view class="item-right-bottom">
                                    <text>{{item.comment}}</text>
                                </view>
                            </view>
                        </view>
                    </view>
                    <image v-else class="nopl" src="/static/course/nopl.png" mode="aspectFit"></image>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
    import { mapGetters, mapState } from 'vuex'
    import httpApi from '@/api/http'
	import uniSection from '@/components/uni-section/uni-section.vue'
    export default {
            components:{
            },
            data() {
                return {
					 btnnum: 0,
					 comment: '',
					 courseSectionList: [],
					 kechengList: ['章节目录', '用户评论'],
    				 sectionNodeId: null,
    				 videoStart: 0,
    				 videoPlayTimer: null, // 视频播放计时器
    				 videoPlayTime: 0,
    				 videoPlayTimeSum: 0,
    				 menuinfo: [],
    				 videoSrc: '',
    				 videoControls: true,
    				 videofullscreen: false
                }
            },
    		
    		computed: {
    		    ...mapGetters({
    		        courseSectionNodeInfo: 'courseInfo/getCourseSectionNodeInfo',
					courseInfo: 'courseInfo/getCourseInfo',
    		        host: 'common/host',
    		        fileHost: 'common/fileHost'
    		    })
    		},
    
            onShareAppMessage(res) {
    
            },
    		
    		destroyed () {
    			this.clearVideoPlayTimer()
    			
    		},
    		
            onReady: function(res) {
                // #ifndef MP-ALIPAY
                this.videoContext = uni.createVideoContext('myVideo')
                // #endif
    
            },
            onLoad(e) {
    		//	let videoInfo = JSON.parse(this.courseSectionNodeInfo.videoUrl)
    			let videoUrl = this.courseSectionNodeInfo.videoUrl
    			this.videoSrc = this.fileHost + videoUrl
    			this.sectionNodeId = e.id
				this.getCourseSectionList()
    		//	this.video = courseSectionNodeInfo.videoInfo.
             //  this.videosrc = 'https://education-edu-13062121691253719016.cos.ap-nanjing.myqcloud.com/videos/2022/01/05/211531-1a80dd24512f4a0880e15e11ea4dd978.mp4'
            },
    		
            onShow(){
               
            },
    
            onUnload() {
                this.menuid = ''
                this.sonid = ''
            },
    
            methods: {
				
				muluchange(e) {
				    this.btnnum = e
				},
    			
				getCourseSectionList () {
					this.$httpApi.get('/student/courseInfo/' + this.courseInfo.id + '/section')
							.then(res => {
								this.courseSectionList = res.data
							})
				},
				
				playVideo(item) {
					if (!item.videoUrl) {
					    uni.showToast({
					    	icon: 'none',
					    	title: '管理员暂未上传视频!'
					    });
						return false
					}
					let videoUrl = item.videoUrl
					this.videoSrc = this.fileHost + videoUrl
				},
    			
    			
               videoErrorCallback(e) {
                   uni.showModal({
                       content: e.target.errMsg,
                       showCancel: false
                   })
    			   this.clearVideoPlayTimer()
               },
    		   
    		   videoTimeUpdate(e){
    		       
    		   },
    		   
    		   videoPlayWait () {
    			   this.clearVideoPlayTimer()
    		   },
    		   
    		   
    		   fullscreen(e){
    		       this.videofullscreen=e.detail.fullScreen
    		   },
    		   
    		   videoOnplay(){
    		      this.videoPlayTimer = setInterval(() => {
    		        this.startCountVideoPlayTime() // 开始计算考试时间
    		     }, 1000)
    		   },
    		   
    		   startCountVideoPlayTime () {
    			   this.videoPlayTime++
    		   },
    		   
    		   videoOnpause() {
    			  this.clearVideoPlayTimer()
    		   },
    		   
    		   videoOnend(){
    			   this.clearVideoPlayTimer()
    		   },
    		   
    		   clearVideoPlayTimer () {
    			   this.videoPlayTimeSum += this.videoPlayTime
    			   this.videoPlayTimer = 0 // 重新设置为0
    			   clearInterval(this.videoPlayTimer)
    		   },
    		   
    		   saveSectionNodeHistory() {
    			   this.$httpApi.post('/student/section/video', {
    			   	userName: this.userName,
    			   	password: this.password
    			   })
    			   .then(res => {
    
    			   })
    		   }
            }
        }
</script>
<style lang="less" scoped>
    page{
        background-color: #f3f3f3;
        font-family: SimHei;
    }
    .container {
		height: 100%;
        background-color: #FFFFFF;
    }
	
	.uni-section {
	 
	    margin-top: 0px !important;
	    padding: 0 !important; 
	    background-color: transparent !important;
	}
    // 视频部分
    .shipinpart {
       /* height: 630upx; 
        background-color: #fff;*/
        &-media {
            height: 400upx;
            background-color: #fff;
            .video {
                width: 750upx;
                height: 400upx;
            }
            .audio_bg{
                position: absolute;
                z-index: -19;
                transition:all .4s cubic-bezier(0.42,0,0.58,1) 0s;
            }
            .donghua(@DHname){
                @keyframes @DHname {
                    0%{
                        filter: blur(0upx);
                    }
                    5%{
                        filter: blur(1upx);
                    }
                    10%{
                        filter: blur(2upx);
                    }
                    15%{
                        filter: blur(3upx);
                    }
                    20%{
                        filter: blur(4upx);
                    }
                    25%{
                        filter: blur(5upx);
                    }
                    30%{
                        filter: blur(6upx);
                    }
                    35%{
                        filter: blur(7upx);
                    }
                    40%{
                        filter: blur(8upx);
                    }
                    45%{
                        filter: blur(9upx);
                    }
                    50%{
                        filter: blur(10upx);
                    }
                    55%{
                        filter: blur(9upx);
                    }
                    60%{
                        filter: blur(8upx);
                    }
                    65%{
                        filter: blur(7upx);
                    }
                    70%{
                        filter: blur(6upx);
                    }
                    75%{
                        filter: blur(5upx);
                    }
                    80%{
                        filter: blur(4upx);
                    }
                    85%{
                        filter: blur(3upx);
                    }
                    90%{
                        filter: blur(2upx);
                    }
                    95%{
                        filter: blur(1upx);
                    }
                    100%{
                        filter: blur(0upx);
                    }
                }
            };
            .donghua(myDongHua);
            .animation(@animation-name,@animation-duration,@animation-iteration-count){
                animation: @arguments;
            }
            .gaosi_filter{
                .animation(myDongHua,4s,infinite);
                transition:all 4s cubic-bezier(0.42,0,0.58,1) 0s;
            }
            image {
                width: 100%;
                height: 400upx;
            }
            .audio {
                width: 100%;
                height: 400upx;
                position: relative;
                display: flex;
                justify-content: center;
                align-items: center;
                z-index: 19;
                padding-bottom: 20upx;
                .bofang {
                    width: 80upx;
                    height: 80upx;
                    image {
                        width: 80upx;
                        height: 80upx;
                    }
                }
                &-wrapper {
                    width:100%;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    position: absolute;
                    bottom: 0;
                    color: #333;
                    background-image: linear-gradient(to bottom, rgba(255,255,255,.25), #fff);
                    .prev, .next {
                        width: 48upx;
                        height: 44upx;
                        margin: 0 20upx;
                    }
                    .audio-number {
                        font-size: 24upx;
                    }
                    .audio-slider {
                        flex: 1;
                    }
                }
            }
        }
        &-info {
            z-index: 19;
            padding: 0 20upx;
            display: flex;
            flex-direction: column;
            .info-span1 {
                margin-top: 35upx;
                font-size: 32upx;
                font-weight: 700;
                color: #070707;
                letter-spacing: 5upx;
            }
            .info-span2 {
                letter-spacing: 5upx;
                font-size: 26upx;
                color: #373737;
                margin-top: 16upx;
                display: flex;
                align-items: center;
                justify-content: space-between;
                text:nth-child(2) {
                    letter-spacing: 1upx;
                    width: 110upx;
                    height: 43upx;
                    border-radius: 10upx;
                    background-color: #e4edff;
                    color: #4b89ff;
                    text-align: center;
                    line-height: 43upx;
                    margin-left: 50upx;
                    margin-right: 30upx;
                    box-shadow:0upx 2upx 3upx 1upx #8dbeff;
                }
            }
            .info-span5{
                display: flex;
                align-items: center;
                justify-content: space-between;
            }
            .info-span3 {
                margin-top: 18upx;
                font-size: 24upx;
                color: #b3b3b3;
                display: flex;
                align-items: center;
                text:nth-child(2) {
                    margin: 0 15upx;
                }
            }
            .info-span4 {
                margin-top: 22upx;
                color: #ff6229;
                font-size: 32upx;
                margin-right: 40upx;
            }
        }
    }


    // 课程部分
    .kechengpart {
        margin-top: 10upx;
        &-title{
            height: 125upx;
            // background-color: red;
            // background-color: #fff;
            padding-top: 45upx;
            box-sizing: border-box;
            display: flex;
            justify-content: space-around;
            &-item {
                flex: 1;
                display: flex;
                flex-direction: column;
                align-items: center;
                view {
                    font-size: 30upx;
                    color: #575757;
                }
                .btna { // 需要追加到view上的class
                    font-weight: 700;
                    color: #131313;
                }
                ._underline { // 需要追加到view下方的下划线class
                    width: 70upx;
                    height: 7upx;
                    background-color: #2f77ff;
                    border-radius: 5upx;
                    margin-top: 15upx;
                }
            }

        }

        &-content {
            // 课程简介
            .kcjs {
                padding: 0 20upx;
                background-color: #fff;
                display: none;
                &-lecturer {
                    &-top {
                        height: 60upx;
                        display: inline-flex;
                        image {
                            width: 47upx;
                            height: 33upx;
                            margin-top: 4upx;
                        }
                        text {
                            font-size: 26upx;
                            font-weight: 700;
                            color: #020202;
                            margin-left: 15upx;
                        }
                    }
                    .jiangshi-right {
                        display: inline-flex;
                        align-items: center;
                        float: right;
                        text {
                            font-size: 22upx;
                            color: #6d6d6d;
                            margin-right: 0;
                        }
                        image {
                            width: 20upx;
                            height: 22upx;
                            margin-left: 5upx;
                        }
                    }
                    &-bottom {
                        display: flex;
                        align-items: center;
                        justify-content: space-between;
                        image{
                            width: 100%;
                            height: 400upx;
                        }
                        .jiangshi-left {
                            display: flex;
                            align-items: center;
                            image {
                                width: 80upx;
                                height: 80upx;
                                border-radius: 40upx;
                            }
                            text {
                                font-size: 26upx;
                                color: #333;
                                margin-left: 20upx;
                            }
                        }
                        .jiangshi-right {
                            display: flex;
                            align-items: center;
                            text {
                                font-size: 26upx;
                                color: #C0C0C0;
                                margin-right: 0;
                            }
                            image {
                                width: 20upx;
                                height: 26upx;
                                margin-left: 5upx;
                            }
                        }

                    }
                }
                &-brief{
                    margin-top: 40upx;
                    margin-bottom: 150upx;
                    &-top {
                        height: 75upx;
                        display: flex;
                        image {
                            width: 53upx;
                            height: 49upx;
                        }
                        text {
                            font-size: 26upx;
                            font-weight: 700;
                            color: #020202;
                            margin-left: 15upx;
                            margin-top: 4upx;
                        }
                    }
                    &-center {
                        padding-bottom:30upx;
                        text {
                            font-size: 26upx;
                            color: #313131;
                        }
                    }
                }
            }

            // 课程目录
            .kcml {
                display: none;
                padding: 0 20upx;
                background-color: #fff;
                &-list {
                    padding: 20upx;
                    box-sizing: border-box;
                    display: flex;
                    justify-content: space-between;
                    border-bottom: 1rpx solid #eee;
                    &-left {
                        width: 436upx;
                        height: 60upx;
                        line-height: 60upx;
                        .yinpin {
                            display: inline-block;
                            width: 64upx;
                            height: 32upx;
                            font-size: 20upx;
                            color: #ff6969;
                            border: 2upx solid #ff6969;
                            border-radius: 5upx;
                            box-sizing: border-box;
                            text-align: center;
                            line-height: 28upx;
                            margin-right: 15upx;
                            vertical-align: middle;
                        }
                        .shipin {
                            display: inline-block;
                            width: 64upx;
                            height: 32upx;
                            font-size: 20upx;
                            color: #398cff;
                            border: 2upx solid #398cff;
                            border-radius: 5upx;
                            box-sizing: border-box;
                            text-align: center;
                            line-height: 24upx;
                            margin-right: 15upx;
                            vertical-align: middle;
                        }
                        .tuwen {
                            display: inline-block;
                            width: 64upx;
                            height: 32upx;
                            font-size: 20upx;
                            color: #8bc34a;
                            border: 2upx solid #8bc34a;
                            border-radius: 5upx;
                            box-sizing: border-box;
                            text-align: center;
                            line-height: 24upx;
                            margin-right: 15upx;
                            vertical-align: middle;
                        }
                        text:nth-child(2) {
                            font-size: 25upx;
                            color: #070707;
                            display: inline-block;
                            white-space: nowrap;
                            width: 80%;
                            overflow: hidden;
                            text-overflow: ellipsis;
                            vertical-align: middle;
                        }
                    }
                    &-right {
                        text {
                            font-size: 24upx;
                            color: #007AFF;
                            vertical-align: middle;
                        }
                        image {
                            width: 30upx;
                            height: 30upx;
                            vertical-align: middle;
                        }
                    }
                }
            }

            // 用户评价
            .yhpj {
                display: none;
                text-align: center;
                margin-bottom: 140upx;
                background-color: #fff;
                padding: 30rpx 0;
                &-list {
                    .item {
                        display: flex;
                        padding: 0 20upx;
                        margin-top: 40upx;
                        &-left {
                            width: 100upx;
                            height: 100upx;
                            border-radius: 50%;
                            margin-right: 20upx;
                        }
                        &-right {
                            display: flex;
                            flex-direction: column;
                            &-top {
                                display: flex;
                                align-items: center;
                                text:nth-child(1) {
                                    font-size: 32upx;
                                    color: #333;
                                }
                                text:nth-child(2) {
                                    font-size: 26upx;
                                    color: #999;
                                    margin-left: 20upx;
                                }
                            }
                            &-bottom {
                                max-width: 500upx;
                                // height: 60upx;
                                margin-top: 10upx;
                                padding: 20upx;
                                border-radius: 10upx;
                                background-color: #fff;
                                text-align: left;
                                text {
                                    font-size: 30upx;
                                    font-weight: 400;
                                    color: #333;
                                }
                            }
                        }
                    }
                }
            }
            .nopl{
                width: 334upx;
                height: 243upx;
                margin-top: 20upx;
            }
            .dis{
                display: block;
            }
        }
    }


    .buy {
        width: 100%;
        height: 122upx;
        background-color: #fff;
        position: fixed;
        bottom: 0;
        display: flex;
        justify-content: space-evenly;
        align-items: center;
        border-radius: 40upx 40upx 0 0;
        border-top: 1rpx solid #eee;
        &-left {
            display: flex;
            flex-direction: column;
            justify-content: space-evenly;
            align-items: center;
            image {
                width: 37upx;
                height: 37upx;
            }
            text {
                margin-top: 15upx;
                font-size: 24upx;
                color: #ff6229;
            }
        }
        .sharebtn {
            margin: 0;
            padding: 0;
            outline: none;
            border-radius: 0;
            background-color: transparent;
            line-height: inherit;
            width: max-content;
        }
        .sharebtn:after {
            border: none;
        }
        &-right {
            width: 450upx;
            height: 80upx;
            background-image: linear-gradient(to right, #4498ff, #1763ff);
            border-radius: 80upx;
            font-size: 34upx;
            font-weight: 700;
            color: #fff;
            // border: 3upx solid #fff;
            text-align: center;
            line-height: 80upx;
            box-shadow: 0rpx 2rpx 2rpx 1rpx #8dbeff;
            letter-spacing: 7rpx;
        }
    }
</style>

