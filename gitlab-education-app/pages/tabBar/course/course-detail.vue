<template>
    <view class="container">

        <!-- 媒体部分 -->
        <view class="shipinpart">
            <view class="shipinpart-media">
                <!-- 视频 -->
                <!-- <video class="video" id="myVideo"
                        :model="menuinfo" :src="videosrc"
                        @error="videoErrorCallback"
                        @timeupdate="video_timeUpdate"
                        @fullscreenchange="fullscreen"
                        @play='video_onplay'
                        @pause='video_onpause'
                        @ended='video_onend'
                        :controls="video_controls"
                        autoplay show-center-play-btn enable-play-gesture>
                 </video> -->

                <!-- 音频 -->
                <!-- <view v-else-if="videostate === 'audio'" class="audio">
                    <image :src="fileHost + courseInfo.headImg" mode="aspectFill" :class="gaosi?'audio_bg gaosi_filter':'audio_bg' "></image>
                    <view class="bofang" @click="play">
                        <image :src="audio_paused?'/static/course/play-big.png':'/static/course/pause1.png'" mode=""></image>
                    </view>
                    <view class="audio-wrapper">
                        <image class="prev" src="/static/course/prev.png" mode="" @click="change_slide(-1)"></image>
                        <view class="audio-number">{{currentTime?currentTime:'00:00'}}</view>
                        <slider class="audio-slider" block-size="16" min="0" max="100" :value="audio_progress" @changing="progress_text" @change="progress_change" activeColor="#2f77ff" backgroundColor="#e9e9e9"></slider>
                        <view class="audio-number">{{durationTime?durationTime:'00:00'}}</view>
                        <image class="next" src="/static/course/next.png" mode="" @click="change_slide(1)"></image>
                    </view>
                </view> -->
                <image :src="fileHost + courseInfo.headImg" mode="aspectFill" ></image>
                <!-- 图片 -->
                <!-- <image v-else :src="menuinfo.thumb?(HOST_URL + menuinfo.thumb):'' " mode="aspectFill" ></image> -->

            </view>
            <view class="shipinpart-info">
                <text class="info-span1">{{courseInfo.name}}</text>
              <!--  <view class="info-span2">
                    <text></text>
                    <text>更新中</text>
                </view> -->
                <view class="info-span5">
                    <text class="info-span3">
                        <text>{{courseInfo.sectionNodeNumber}}课时</text>
                        <text>|</text>
                        <text>{{courseInfo.studyNumber}}人学过</text>
                    </text>
                   <!-- <text class="info-span4" v-if="action=='' ">￥55</text>
                    <text class="info-span4" v-if="action=='credit' ">100积分</text>
                    <text class="info-span4" v-if="action=='seckill' ">秒杀价:￥100</text>
                    <text class="info-span4" v-if="action=='pintuan' ">拼团价:￥100</text> -->
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


                <!-- 课程介绍 -->
                <view class="kcjs" :class="{dis:btnnum == 0}" :model="teacher">
                    <view class="kcjs-lecturer">
                        <!-- <view class="kcjs-lecturer-top">
                            <image src="/static/course/kechengjiangshi.png" mode=""></image>
                            <text>课程讲师</text>
                        </view>
                        <view class="jiangshi-right" @click="jsdetail">
                            <text>讲师主页</text>
                            <image src="/static/course/jt.png" mode=""></image>
                        </view>
                        <view class="kcjs-lecturer-bottom" >
                            <image :src="teacher.img?(HOST_URL + teacher.img):'' " mode="aspectFit"></image>
                        </view> -->
                    </view>
                    <view class="kcjs-brief">
                        <view class="kcjs-brief-top">
                            <image src="/static/course/kechengjianjie.png" mode=""></image>
                            <text>课程简介</text>
                        </view>
                        <view class="kcjs-brief-center">
                            <u-parse :content="courseInfo.represent"></u-parse>
                        </view>
                    </view>
                </view>


                <!-- 课程目录 -->
                <scroll-view scroll-y="true" class="kcml" style="margin-top: 10upx;" :class="{dis:btnnum == 1}">
					<scroll-view v-if="courseSectionList.length > 0" v-for="(section, index) in courseSectionList" :key="index">
						<uni-section :title="'第' + (index + 1) + '章:' + section.title" type="line"></uni-section>
						<view class="kcml-list" 
						  v-for="(node, nodeIndex) in section.courseSectionNodeList"
						  :key="node.id"
						@click="openvideo(node)">
							<view class="kcml-list-left">
								<text class="shipin">视频</text>
								<text>{{index + 1}}-{{nodeIndex + 1}}&nbsp;{{node.title}}</text>
							</view>
							<!-- <view class="kcml-list-right">
								<view>
									<text>试看</text>
								</view>
								<image src="/static/course/colock.png" mode=""></image>
							</view> -->
						</view>
					</scroll-view>
					
					<scroll-view v-else>
						<image  class="nopl" src="/static/course/nopl.png" mode="aspectFit"></image>
					</scroll-view>
					
                </scroll-view>



                <!-- 用户评价 -->
                <view class="yhpj" :class="{dis:btnnum == 2}">
                    <view class="yhpj-list" v-if="courseValuateList.length > 0">
                        <view class="item" v-for="(item, index) in courseValuateList" :key="index">
                            <image class="item-left" :src="item.headImg" mode="aspectFit"></image>
                            <view class="item-right">
                                <view class="item-right-top">
                                    <text>{{item.loginName}}</text>
                                   <!-- <text>{{item.addtime}}</text> -->
                                </view>
                                <view class="item-right-bottom">
                                    <text>{{item.content}}</text>
                                </view>
                            </view>
                        </view>
                    </view>
                   <image v-else class="nopl" src="/static/course/nopl.png" mode="aspectFit"></image>
                </view>
            </view>
        </view>


        <!-- 立即购买 -->
        <!-- <view class="buy" v-if="btnnum <= 2">
            <view class="buy-left" @click="dingyue">
                <image src="/static/course/shoucang.png" mode=""></image>
                <text v-if="!is_dingyue">收藏</text>
                <text v-if="is_dingyue">已收藏</text>
            </view>
            <button class="buy-left sharebtn" open-type="share">
                <image src="/static/course/fenxiang.png" mode=""></image>
                <text style="color: #848484;">分享</text>
            </button> -->
          <!--  <view class="buy-right" @click="pay1" v-if="!is_free && action=='' ">立即购买</view>
            <view class="buy-right" @click="exchange" v-if="!is_free && action=='credit' ">立即兑换</view>
            <view class="buy-right" @click="seckill" v-if="!is_free && action=='seckill' ">立即秒杀</view>
            <view class="buy-right" @click="pintuan" v-if="!is_free && action=='pintuan' ">开始拼团</view>
            <view class="buy-right" v-if="is_free">免费</view> -->
		<!-- 	<view class="buy-right" >免费</view> -->
        </view>

        <!-- <zaudio></zaudio> -->
        <!-- <zaudio></zaudio> -->

    </view>
</template>

<script>
    import { mapGetters, mapState } from 'vuex'
    import httpApi from '@/api/http'
    import uParse from '@/components/uParse/src/wxParse.vue'
	import uniSection from '@/components/uni-section/uni-section.vue'
    // #ifndef MP-WEIXIN
    const innerAudioContext = uni.createInnerAudioContext()
    // #endif
    // #ifdef MP-WEIXIN
    const innerAudioContext = uni.getBackgroundAudioManager();
    // #endif
    export default {
        components:{
            uParse
        },
        data() {
            return {
				courseSectionList: [], // 课程章节列表
                courseId: null,
                kechengList: ['课程介绍', '目录', '评价'],
                btnnum: 0,
                currentTime:'',
                coursemenus: [],
                menuinfo: [],
                teacher: [],
                courses: [],
                comment: [],
                videostate: '',
                durationTime: "", //音频的总时长
                coursehour: 0 ,// 课时
                audio_paused:true,
                audio_progress:0,
                gaosi:false,
                t1:'',
                t2:'',//video计时器id
                t3:'',//audio计时器id
                videosrc:'',
                free_time:'',
                video_controls:true,
                videofullscreen:false,
                menuid:'',
                sonid:'',
                videoStudyTime: 0,//秒
                audioStudyTime: 0,//秒
                is_free:false,
                son_is_pay:false,
                uid:'',
                is_dingyue:false,
                videoContext:{},
                creditinfo:{},
                action:'',
                seckillinfo:{},
                pintuaninfo:{},
				courseValuateList: []
            }
        },

        computed: {
            ...mapGetters({
                courseInfo: 'courseInfo/getCourseInfo',
                host: 'common/host',
                fileHost: 'common/fileHost'
            })
        },

        onShareAppMessage(res) {
            let path = getCurrentPages()
            let path_share = path[0].$page.fullPath
            let path_title = path[0].data.title
            let userinfo = uni.getStorageSync('userinfo')
            let base_set = uni.getStorageSync('base_set')


        },
        onReady: function(res) {
            // #ifndef MP-ALIPAY
            this.videoContext = uni.createVideoContext('myVideo')
            // #endif

        },
        onLoad(e) {
            console.log(this.courseInfo)
			 this.getCourseSectionList()
			 this.getCourseValuateList()
        },
        onShow(){
            /*
                        audio.onSeeked((e) => {
                            this.currentTime = this.format(audio.currentTime)
                        })
                        audio.onPlay((e) => {
                            this.videoContext.pause()
                            this.audio_paused=false
                            this.gaosi_ani()
                            this.gaosi=true
                            this.countAtime()
                        })
                        audio.onPause((e) => {
                            this.audio_paused=true
                            clearInterval(this.t1)
                            clearInterval(this.t3)
                            this.gaosi=false
                            this.saveAtime()
                        })
                        audio.onStop((e) => {
                            this.audio_paused=true
                            clearInterval(this.t1)
                            clearInterval(this.t3)
                            this.saveAtime()
                        })
                        audio.onEnded((e) => {
                            clearInterval(this.t1)
                            clearInterval(this.t3)
                            this.audio_paused=true
                            this.currentTime='00:00'
                            audio.seek(0)
                            this.saveAtime()
                        }) */

        },

        onUnload() {
            clearInterval(this.t1)
            clearInterval(this.t2)
            clearInterval(this.t3)
            this.menuid=''
            this.sonid=''
            this.saveAtime()
            this.saveVtime()

        },

        methods: {
			
			getCourseValuateList () {
				this.$httpApi.get('/student/courseValuate', {
					courseId: this.courseInfo.id
				}).then(res => {
					this.courseValuateList = res.data.dataList
				})
			},

		    getCourseSectionList () {
				this.$httpApi.get('/student/courseInfo/' + this.courseInfo.id + '/section')
						.then(res => {
							this.courseSectionList = res.data
						})
			},
           
            jsdetail(){
                uni.navigateTo({
                    url:'../tutor-introduced/tutor-introduced?tid='+this.teacher.id
                })
            },
            get_creditinfo(id,goodstype){
                const BASE_URL = uni.BASE_URL
                uni.request({
                    url: BASE_URL+'index/credit/creditinfo',
                    data: {
                        id:id,
                        goodstype:goodstype
                    },
                    method:'POST',
                    success:(res) =>{
                        console.log(res.data)
                        this.creditinfo=res.data.data
                    },
                    fail:(res)=> {
                        console.log(res.data);
                    }
                });
            },
            get_seckillinfo(id,goodstype){
                const BASE_URL = uni.BASE_URL
                uni.request({
                    url: BASE_URL+'index/seckill/seckillinfo',
                    data: {
                        id:id,
                        goodstype:goodstype
                    },
                    method:'POST',
                    success:(res) =>{
                        console.log(res.data)
                        this.seckillinfo=res.data.data
                    },
                    fail:(res)=> {
                        console.log(res.data);
                    }
                });
            },
            get_pintuaninfo(id,goodstype){
                const BASE_URL = uni.BASE_URL
                uni.request({
                    url: BASE_URL+'index/pintuan/pintuaninfo',
                    data: {
                        id:id,
                        goodstype:goodstype
                    },
                    method:'POST',
                    success:(res) =>{
                        console.log(res.data)
                        this.pintuaninfo=res.data.data
                    },
                    fail:(res)=> {
                        console.log(res.data);
                    }
                });
            },
            saveStudytime(studytime,media){
                const BASE_URL = uni.BASE_URL
                uni.request({
                    url: BASE_URL+'index/user/save_studytime',
                    method:'POST',
                    data:{
                        uid:this.uid,
                        media:media,
                        studytime:studytime
                    },
                    success:(res) =>{
                        console.log(res.data);
                    },
                    fail:(res)=> {
                        console.log(res.data);
                    }
                });
            },
            saveAtime(){
                var atime=uni.getStorageSync('atime')
                var data={}
                if(atime){
                    data.atime=atime.atime+this.audioStudyTime
                    data.nowtime=(new Date()).getTime()
                    uni.setStorageSync('atime',data)
                    this.saveStudytime(data.atime,'audio')
                }else{
                    data.atime=this.audioStudyTime
                    data.nowtime=(new Date()).getTime()
                    uni.setStorageSync('atime',data)
                    this.saveStudytime(data.atime,'audio')
                }
            },
            saveVtime(){
                var vtime=uni.getStorageSync('vtime')
                var data={}
                if(vtime){
                    data.vtime=vtime.vtime+this.videoStudyTime
                    data.nowtime=(new Date()).getTime()
                    uni.setStorageSync('vtime',data)
                    this.saveStudytime(data.vtime,'video')
                }else{
                    data.vtime=this.videoStudyTime
                    data.nowtime=(new Date()).getTime()
                    uni.setStorageSync('vtime',data)
                    this.saveStudytime(data.vtime,'video')
                }
            },
            countVtime(){
                this.t2=setInterval(()=>{
                    this.videoStudyTime++
                    //console.log(this.videoStudyTime)
                },1000)
            },
            countAtime(){
                this.t3=setInterval(()=>{
                    this.audioStudyTime++
                    //console.log(this.audioStudyTime)
                },1000)
            },
            dingyue(){
                const BASE_URL = uni.BASE_URL
                uni.request({
                    url: BASE_URL+'index/courses/dingyue',
                    method:'POST',
                    data:{
                        uid:this.uid,
                        media:this.menuinfo.media,
                        menuid:this.menuid,
                        goodstype:'course'
                    },
                    success:(res) =>{
                        console.log(res.data);
                        this.is_dingyue=res.data.data==1?true:false
                    },
                    fail:(res)=> {
                        console.log(res.data);
                    }
                });
            },
            pay(){
                if(this.sonid==''){
                    uni.navigateTo({
                        url:'/pages/confirm-order-form/confirm-order-form?menuid='+this.menuid+'&goodstype=course'
                    })
                }else{
                    uni.navigateTo({
                        url:'/pages/confirm-order-form/confirm-order-form?menuid='+this.menuid+'&sonid='+this.sonid+'&goodstype=course'
                    })
                }
            },
            pay1(){
                uni.navigateTo({
                    url:'/pages/confirm-order-form/confirm-order-form?menuid='+this.menuid+'&goodstype=course'
                })
            },
            exchange(){
                uni.navigateTo({
                    url:'/pages/confirm-order-form/confirm-order-form?menuid='+this.menuid+'&goodstype=course&action=credit'
                })
            },
            seckill(){
                uni.navigateTo({
                    url:'/pages/confirm-order-form/confirm-order-form?menuid='+this.menuid+'&goodstype=course&action=seckill'
                })
            },
            pintuan(){
                uni.navigateTo({
                    url:'/pages/confirm-order-form/confirm-order-form?menuid='+this.menuid+'&goodstype=course&action=pintuan'
                })
            },
            fullscreen(e){
                this.videofullscreen=e.detail.fullScreen
            },
            video_onplay(){
                this.countVtime()
                let audio=innerAudioContext;
                audio.pause()
            },
            video_onpause(){
                clearInterval(this.t2)
                this.saveVtime()
            },
            video_onend(){
                clearInterval(this.t2)
                this.saveVtime()
            },
            video_timeUpdate(e){
                let video_currentTime=e.detail.currentTime
                let video_duration=e.detail.duration
                if(!this.is_free || !this.son_is_pay){
                    if(!this.son_is_pay){
                        if(video_currentTime>=this.free_time){
                            if(this.videofullscreen){
                                this.videoContext.exitFullScreen()
                            }
                            this.videoContext.pause()
                            uni.showModal({
                                title: '试看结束',
                                content: '需要解锁该课程吗?',
                                success: function (res) {
                                    if (res.confirm) {
                                        this.pay()
                                    } else if (res.cancel) {
                                        this.videoContext.seek(0)
                                    }
                                }
                            });
                        }
                    }
                }
            },
            gaosi_ani(){
                if(this.gaosi==true){
                    this.t1=setInterval(function(){
                        this.gaosi=false
                        if(this.gaosi==false){
                            this.gaosi=true
                        }
                    },4000)
                }
            },
            progress_text(e){
                let audio=innerAudioContext
                let progress=e.detail.value
                this.currentTime = this.format((progress/100)*audio.duration)
            },
            progress_change(e){
                let audio=innerAudioContext
                let progress=e.detail.value
                this.currentTime = this.format((progress/100)*audio.duration)
                audio.seek((progress/100)*audio.duration)
            },
            change_slide(e){
                let audio=innerAudioContext;
                if(e==1){
                    audio.seek(audio.currentTime+15)
                }else{
                    audio.seek(audio.currentTime-15)
                }
            },
            play(){
                let audio=innerAudioContext;
                if(audio.paused){
                    audio.play()
                    //this.audio_paused=false
                }else{
                    audio.pause()
                    //this.audio_paused=true
                }
            },

            muluchange(e) {
                this.btnnum = e
            },

            // 获取课程章节
            getCourseSection() {

            },

            videoErrorCallback(e) {
                uni.showModal({
                    content: e.target.errMsg,
                    showCancel: false
                })
            },
            openvideo (item) {
				if (!item.videoUrl) {
				    uni.showToast({
				    	icon: 'none',
				    	title: '管理员暂未上传视频!'
				    });
					return false
				}
						
				this.$store.commit('courseInfo/updateCourseSectionNodeInfo', item)
				uni.navigateTo({
					url: './coursePlay?id=' + item.id
				})
            },
            //格式化时长
            format(num) {
                return '0'.repeat(2 - String(Math.floor(num / 60)).length) + Math.floor(num / 60) + ':' + '0'.repeat(2 - String(
                    Math.floor(num % 60)).length) + Math.floor(num % 60)
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
        background-color: #f3f3f3;
    }
	
	.uni-section {
	 
	    margin-top: 0px !important;
	    padding: 0 !important; 
	    background-color: transparent !important;
	}
    // 视频部分
    .shipinpart {
        height: 630upx;
        background-color: #fff;
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
            background-color: #fff;
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
                    margin-top: 12upx;
                    margin-bottom: 150upx;
                    &-top {
						padding-top: 20upx;
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
                        padding: 20upx;
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
				margin: 10upx;
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

