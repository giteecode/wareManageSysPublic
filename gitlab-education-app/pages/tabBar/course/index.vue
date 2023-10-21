<template>
	<view class="container">
		<course-navigator></course-navigator>
		<v-tabs v-model="current" :tabs="subjectNames" @change="changeTab"></v-tabs>
		<view class="course">
			<view class="course-list">
				<view 
					v-for="(course, index) in courseList" 
					:key="index" 
					class="course-item"
					@click="goDetail(course)"
				>
					<image class="course-image" :src="fileHost + course.headImg" mode="aspectFit"></image>
					<view class="course-info">
						<view class="course-title">
							<view class="course-text">{{course.name}}</view>
						</view>
						<view class="course-more-info">
							<view class="course-time">{{course.pushTime}}</view>
							<view class="course-number">{{course.studyNumber}}人学习</view>
						</view>
					</view>
				</view>
				 <uni-load-more :status="status"  :icon-size="16" :content-text="contentText" />
			</view>
           
        </view>
	</view>
</template>


<script>
	import vTabs from '@/uni_modules/v-tabs/components/v-tabs/v-tabs.vue'
	import courseNavigator from '@/components/course-navigator/index.vue'
	import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue'
	import { mapGetters, mapState } from 'vuex'
	import httpApi from '@/api/http'
	export default {
		components: {
			vTabs,
			courseNavigator,
			uniLoadMore
		},
		data() {
			return {
				tabBars: [],
				dataList:[],
                subjectNames: [],
				current: 0,
				tabIndex: 0,
				defaultindex:1 ,//默认让下标为1的数据选中，下标从0开始
				courseList: [],
				currentPage: 1,
				pageSize: 6,
				status: 'loading',
				contentText: {
					contentdown: '上拉加载更多',
					contentrefresh: '数据加载中',
					contentnomore: '亲, 没有更多数据啦'
				},
				loadingFirst: false // 是否上拉刷新列表
			}
		},
		
		computed: {
			...mapGetters({
				host: 'common/host',
				fileHost: 'common/fileHost'
			})
		},
		
		onLoad () {
           this.getSubjectList()
		},
		
		onPullDownRefresh() {
			this.loadingFirst = true
		    this.getCourseList()
		},
		
		onReachBottom() {
			this.currentPage++
			this.getCourseList(0)
		},
		
		methods: {
			
			changeTab(index) {
			  console.log('当前选中的项：' + index)
			  this.getCourseList(index)
			},

		  getSubjectList () {
			httpApi.get('/student/subject')
				.then(res => {
				  this.tabBars = res.data
				  let result = res.data
				  result.forEach(item => {
					this.subjectNames.push(item.name)
				  })
				  this.getCourseList(0)
				})
		  },
			
			// 获取课程列表
			getCourseList(index) {
				let subjectId = this.tabBars[index].id
				let params = {
					subjectId: subjectId,
					pageNumber: this.currentPage, 
					pageSize: this.pageSize,
					status: 1
				}
				this.$httpApi.get('/student/courseInfo', params)
				.then(res => {
					let result = res.data
					if (result.dataList.length === 0
					|| result.dataList.length < this.pageSize) {
						this.status = 'noMore'
					}
					if (this.loadingFirst || this.tabIndex != index) {
						this.tabIndex = index
						this.courseList = []
						setTimeout(() => {
							this.courseList = result.dataList
							this.loadingFirst = false
							uni.stopPullDownRefresh()
						}, 800)
					} else {
						this.courseList = this.courseList.concat(result.dataList)
					}
				})
			},
			
			// 进入课程
			goDetail (item) {
				this.$store.commit('courseInfo/updateCourseInfo', item)
				uni.navigateTo({
					url: '../course/course-detail'
				})
			},
		},
		
	}
</script>
<style>
	.container{
		background:#efeff4;
	}
	.course-list-top{
		background:#fff;
	}
	.course-list{
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: space-between;
		padding: 2.5vw;
		padding-bottom:50px;
	}
	.course-item{
		width:46vw;
		background:#fff;
		margin-bottom:3vw;
		border-radius:12upx;
		overflow: hidden;
	}
	.course-image{
		height:25.875vw;
	}
	.course-info{
		padding:16upx;
	}
	.course-title{ 
		height:80upx;
	}
	.course-text{
		font-size:14px;
		font-weight:500;
		color:#465d63;
		line-height:20px;
		text-align: justify;
		overflow: hidden;
		-webkit-line-clamp: 2;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-box-orient: vertical;  
	}
	.course-more-info{
		display:flex;
	}
	.course-time{
		font-size:12px;
		color:#7d8f94bb;
		margin-right:20upx;
	}
	.course-number{
		font-size:12px;
		color:#7d8f94bb;
	}
	.uni-load-more {
		margin-left: 100px;
		height: 100%;
		width: 50%;
	}
	</style>