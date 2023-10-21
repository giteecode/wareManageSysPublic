<template>
	<view class="content">
		<view class="exam-page">
			<form>
				<swiper
				 :current="questionIndex"
				  class="swiper-box" @change="swiperChange" 
				  :style="{'height':swiperHeight}">
					<swiper-item v-for="(questionInfo, index) in wrongBookQuestionAnswerList">
						<scroll-view class="scroll-v list" :style="{'height':swiperHeight}" :scroll-y="true">
							<view class="question-content">
								<view class="question-type">
									<view class="cu-tag bg-red round">{{currentQuestionName}}</view>
								</view>
								
								<!-- 题目 -->
								<view class="padding questionInfo_content">
									<uParse :content="questionInfo.content"/>
								</view>
								
								<view>
									<!-- 判断题 -->
									<view v-if='questionInfo.questionType === 6'>
										<view v-for="(item,index) in tfQuest" :key="index">
											<view class="content-solutions-item" :class="questionInfo.studentAnswer===index ? 'active' : ''">
												<view class="content-solutions-item-number" v-html="item.icon"></view>
												<view class="content-solutions-item-text" v-html="item.label" @tap='radioboxChange(index)'></view>
											</view>
										</view>
									</view>
									
									<!-- 单选题 -->  
									<view v-else-if='questionInfo.questionType === 1'>
										<view v-for="(item,index) in questionInfo.optionList" :key="index">
											<view class="content-solutions-item" :class="questionInfo.studentAnswer===item.label ? 'active' : ''">
												<view class="content-solutions-item-number">{{item.label}}</view>
												<view class="content-solutions-item-text" v-html="item.option_name" @tap='radioboxChange(item.label)'></view>
											</view>
										</view>
									</view>
									
									<!-- 多选题 -->
									<view v-else-if='questionInfo.questionType === 2'>
										<view v-for="(item,index) in questionInfo.optionList" :key="index">
											<view class="content-solutions-item" :class="[labelInAnswer(questionInfo.studentAnswer,item.label)]">
												<view class="content-solutions-item-number">{{item.label}}</view>
												<view class="content-solutions-item-text" v-html="item.option_name" @tap='checkboxChange(item.label,questionInfo.studentAnswer)'></view>
											</view>
										</view>
									</view>
									
									<view style="margin-bottom: 50px;" v-else>
										<el-upload
										@onChoose="uploadImage"
										@onDelete="deleteImage"
										:imgList="questionInfo.studentAnswerProxy" />
									</view>
									
								</view>

								<!-- 解析 -->
								<view class="margin-top solid-top">
									<view class="cu-bar">
										<view class="action  text-grey">
										  <text>批改结果：{{questionInfo.correctStatus | getCorrectStatus}}
										  </text>
									</view>
								</view>
							
								<view class="cu-bar">
									<view class="action  text-grey">
										<text>分值：{{questionInfo.questionMark}}分</text>
									</view>
								</view>

							    <view class="cu-bar">
									<view class="action  text-grey">
									  <text>得分：{{questionInfo.studentMark}}分</text>
									</view>
							    </view>
							
								<view class="cu-bar">
									<view v-if="isSubjectiveQuestion(questionInfo.questionType)" class="action  text-grey">
										<text>正确答案：</text>
										<uParse :content="questionInfo.answer"/>
									</view>
									<view v-else class="action  text-grey">
										<text>
											正确答案：{{questionInfo.answer |
											 parseAnswer(questionInfo.questionType)}}
										</text>
									</view>
								</view>
							

								<view class="cu-bar">
									<view 
									 v-if="!isSubjectiveQuestion(questionInfo.questionType)"
									 class="action  text-grey">
										<text>
										你的答案：{{questionInfo.studentAnswer | parseAnswer(questionInfo.questionType)}}
										</text>
									</view>
									
									 <view v-else
									  class="action  text-grey">
										<text>
										你的答案：
										</text>
										
										<view v-if="questionInfo.studentAnswer" style="margin-bottom: 50px;">
											<el-upload :imgList="questionInfo.studentAnswer"/>
										</view>
										
										<text v-else>未作答</text>
									</view> 
								</view>
									
								<view class="cu-bar">
									<view class="action  text-grey">
										<text>解析：</text>
									</view>
								</view>
								
								<view 
								class="text-content padding  text-grey">
								   <uParse :content="questionInfo.analysis"/>
								</view>
							   </view>
							</view>
						 </scroll-view>
					</swiper-item>
				</swiper>
			</form>
			
			<!-- 底部功能条 -->
			<view id="foot-box" class="cu-bar tabbar bg-white shadow foot">
				<view class="action" @click="changeQuestionInfo(-1)">
					 <view class="cuIcon-cu-image">
						<text class="lg cuIcon-back text-gray"></text>
					</view> 
					<view class="text-gray">上一题</view> 
					
				</view>
				<view class="action" @click="changeQuestionInfo(1)">
					 <view class="cuIcon-cu-image">
						<text class="lg text-gray cuIcon-right"></text>
					</view>
					<view class="text-gray">下一题</view>
				</view>
				
				<view class="action" @click="favorQuestion">
					<view class="cuIcon-cu-image">
						<text class="lg cuIcon-favor" :class="[userFavor?'text-red':'text-gray']"></text>
					</view>
					<view  :class="[userFavor?'text-red':'text-gray']">收藏</view>
				</view>
			</view>
		
		</view>
		
		<!-- 提示 -->
		<uni-popup
		  id="questionDialog" 
		  ref="questionDialog" 
		  type="dialog">
			<uni-popup-dialog 
			  type="wran"
			  :content="commitErrorMsg" 
			  :before-close="true"
			  @confirm="questionDialogConfirm" 
			  @close="questionDialogClose"></uni-popup-dialog>
		</uni-popup>
		
		
		<!-- 纠错卡 -->
		<view class="cu-modal padding " :class="modalError=='modalError'?'show':''" @tap="hideErrorModal">
			<view class="cu-dialog bg-white" @tap.stop>								
				
				<view class="cu-bar solid-bottom ">
					<view class="action">
						<text class="cuIcon-title text-red"></text>试题纠错
					</view>					
				</view>
				
				<radio-group class="block" >
					<view class="cu-list menu text-left">
					<view class="cu-item cu-item-error" v-for="error in errorList" >
						<radio :value="error"></radio>
						<view class="title text-black margin-left">{{error}}</view>
					</view>	
					</view>	
				</radio-group>				
				
				<view class="padding flex flex-direction ">				
					<button class="cu-btn bg-red margin-tb-sm lg" @click="SubmitError">提 交</button>
				</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	import uniTag from '@/components/uni-tag/uni-tag.vue'
	import uniPopup from '@/components/uni-popup/uni-popup.vue'
	import uniIcons from '@/components/uni-icons/uni-icons.vue'
	import uParse from '@/components/uParse/src/wxParse.vue'
	import elUpload from '@/components/upload.vue'
	import uniCountdown from '@/components/uni-countdown/uni-countdown.vue'
	import { mapGetters } from 'vuex'
	export default {
		components: {uniTag, uniPopup, uniIcons, uParse, elUpload, uniCountdown },
		data() {
			return {
				pageNumber: 1,
				pageSize: 10,
				userFavor: false,//是否已收藏
				wrongBookQuestionAnswerList: [],
				questionCount: null,
				questionTotal: 0, // 试题数量
				currentQuestionName: '', // 当前试题名称
				currentType: 0, //当前题型
				questionIndex: 0,//跳转索引
				autoShowAnswer: false,//答错是否显答案
				autoRadioNext:true,//判断题、单项题，自动移下一题
				swiperHeight: '800px',//
				title: '',
				modalCard: null ,//显示答题卡
				modalError: null , //纠错卡
				commitErrorMsg: '',
				errorList:['题目不完整', '答案不正确', '含有错别字', '图片不存在', '解析不完整', '其他错误']
			}
		},
		
		computed: {
		   ...mapGetters({
				fileHost: 'common/fileHost',
				wrongBook: 'wrongBook/getWrongBook'
		   }),
		},
		
		onReady() {
			
        },
		
		destroyed () {
			
		},

		onLoad(params) {
		  this.getBizWrongBookList()
		},
		
		methods: {

		  // 获取错题列表
			getBizWrongBookList() {
				this.$httpApi.get('/student/wrongBook', {
					pageNumber: this.pageNumber,
					pageSize: this.pageSize,
					bizId: this.wrongBook.bizId,
					bizType: this.wrongBook.type,
				}).then(res => {
					let result = res.data.dataList	
					let questionIndex = 0
					result.forEach(subItem => {
						++questionIndex
						if (subItem.questionType === 1 || subItem.questionType === 2) {
							 let options = subItem.options
							 if (options) {
								 subItem.optionList = JSON.parse(options)
								 
							 }
						}
													 // 试题类型非单选和判断题， 解析答案数组
						if (subItem.questionType !== 1 && subItem.questionType !== 6) {
						   let studentAnswerStr = subItem.studentAnswer
						   let studentAnswerArray = []
						   if (studentAnswerStr) {
							 studentAnswerStr.split(',').forEach(value => {
							   if (value && subItem.questionType !== 2) {
								 studentAnswerArray.push(this.fileHost + value)
							   } else {
								 // 多选题
								 studentAnswerArray.push(value)
								
							   }
							 })
							 subItem.studentAnswer = studentAnswerArray
						   }
						   
						   if (subItem.optionList) {
							   subItem.optionList.forEach(option => {
									let value = option.label
									option.checked = false
									if (subItem.studentAnswer.length > 0) {
										subItem.studentAnswer.forEach(answer => {
											if (answer === value) {
												option.checked = true
											}
										})
									} 
							   }) 
						   }
						   
						}
						this.wrongBookQuestionAnswerList.push(subItem)
					})
					this.questionTotal = this.wrongBookQuestionAnswerList.length
					this.questionCount = 1 + '/' + this.questionTotal
					this.currentQuestionName = this.wrongBookQuestionAnswerList[0].questionTypeName
				})
			},
			
			
			isSubjectiveQuestion(questionType) {
				if (questionType !== 1 && questionType !== 2 && questionType !== 6) {
					return true
				}
				return false
			},
			
			showCardModal (e) {
				this.modalCard = e.currentTarget.dataset.target
			},
			
			hideCardModal (e) {
				this.modalCard = null
			},
			
			showErrorModal (e) {
				this.modalError = e.currentTarget.dataset.target
			},
			
			hideErrorModal (e) {
				this.modalError = null
			},
			
			swiperChange (e) { //滑动事件
				let index = e.target.current
				if (index != undefined) {
					this.questionIndex = index
					this.questionCount = this.questionIndex + 1 + '/' + this.questionTotal
					this.currentQuestionName = this.wrongBookQuestionAnswerList[this.questionIndex].questionTypeName
				}								
			},			
			
			
			//上一题、下一题
			changeQuestionInfo (val) {
				
				if (this.questionIndex === 0 && val === -1) {
					uni.showToast({
						icon: 'none',
						title: '亲, 没有上一题了'
					})
					return
				}
				
				if (val === -1 && this.subjectIndex != 0) {
					this.questionIndex -= 1;
				}
				if (val === 1 && this.questionIndex < this.wrongBookQuestionAnswerList.length - 1) {
					this.questionIndex += 1;
				} 
				else if (this.questionIndex  === this.wrongBookQuestionAnswerList.length - 1) {
					uni.showToast({
						icon: 'none',
						title: '亲, 已经是最后一题了'
					})
					return
				}
				if (this.questionIndex > 0) {
					this.questionCount = this.questionIndex + 1 + '/' + this.questionTotal
				}
								
				
			},
			
			// 答题卡试题切换
			appointedQuestion (val) { 
				this.modalCard = null
				this.questionIndex = val									
			},			
			
			SubmitError: function(e) { //提交纠错
				this.modalError = null;														
			}
			
		},

		filters: {

		  getCorrectStatus(val) {
			if (val === 0) {
			  return '错误'
			} else if (val === 1) {
			  return '正确 '
			} else if (val === 2) {
			  return '待批改'
			} else {
			  return '已批改'
			}
		  },
		  
		  parseAnswer (answer, questionType) {
		    if (!answer) {
			   return '未作答'
		    }
		  	if (questionType === 6 && (answer === 1 || answer === '1')) {
		  	  return '对'
		  	} else if (questionType === 6 && (answer === 0 || answer === '0')) {
		  	  return '错'
		  	} else if (questionType === 2 && answer instanceof Array) {
		  	  let answerValue = ''
		  	  answer.forEach(value => {
		  		answerValue += value + ','
		  	  })
		  	  return answerValue.substr(0, answerValue.length - 1)
		  	}
		  	return answer
		  }
		}
	}
</script>

<style scoped>
	/*每个页面公共css */
	page {
		background-color: #FFFFFF;
	}
	
	.exam-page{
		background:#007AFF;
	}
	.exam-top{
		width:100vw;
		padding:16px;
	}
	.exam-top-global-text{
		display: flex;
		justify-content: space-between;
	}
	.exam-top-text{
		display:flex;
		align-items: center;
	}
	
	.exam-top-text-common{
		font-size:14px;
		color:#ffffffbb;
		margin-right:12upx;
	}
	.exam-top-text-primary{
		color:#fff;
		font-size: 16px;
		font-weight:500;
	}
	.progress{
		border-radius:4px;
		overflow: hidden;
		margin:16px 0;
	}
	.question-content{
		min-height:100%;
		margin:0 16px;
		padding:16px;
		background:#fff;
		border-radius:16px 16px 0 0;
	}
	.question-type{
		display:flex;
		/* justify-content: flex-end; */
	}
	.questionInfo_content{
		line-height:20px;
		font-weight:500;
		color:#465d63;
		text-align: justify;
	}

	.content-solutions-item{
		margin: 0 10upx 32upx 10upx;
		display: flex;
		justify-content: flex-start;
		padding: 10px 16px;
		background: #f8f8f8;
		border-radius: 6px;
		border:3upx solid transparent;
	}
	.content-solutions-item.active{
		background:#007AFF11;
		color:#007AFF;
		border:3upx solid #007AFF;
	}
	.content-solutions-item .content-solutions-item-number{
		width:24px;
		line-height:24px;
	}
	.content-solutions-item .content-solutions-item-text{
		flex:1;
		line-height:24px;
	}
	
	
	
	view {
		line-height: 1;
	}
	
	radio {
	   position: unset;	
	}
	
	uni-radio:before {
		content: ''
	}
	
	uni-view {
		font-size: 14px;
	}
	uni-checkbox:before {
		content: ''
	}
    
    .line-green {
	   background-color: #409eff !important;
	   color: #F0F0F0;
	} 
	.cu-form-group {
		justify-content: flex-start
	}

	.cu-form-group .title {
		padding-left: 30upx;
		padding-right: 0upx;
	}

/* 提示窗口 */
	.uni-tip {
		padding: 15px;
		width: 300px;
		background: #fff;
		box-sizing: border-box;
		border-radius: 10px;
	}

	.uni-tip-title {
		text-align: center;
		font-weight: bold;
		font-size: 16px;
		color: #333;
	}

	.uni-tip-content {
		padding: 15px;
		font-size: 14px;
		color: #666;
	}

	.uni-tip-group-button {
		margin-top: 10px;
		display: flex;
	}

	.uni-tip-button {
		width: 100%;
		text-align: center;
		font-size: 14px;
		color: #3b4144;
	}

	.cu-form-group+.cu-form-group {
		border-top: none;
	}

	.cu-bar-title {
		min-height: 50upx;
	}
	.cu-list.menu>.cu-item-error{justify-content: flex-start;}
</style>
