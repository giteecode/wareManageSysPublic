<template>
	<view>
		<scroll-view
			id="tab-bar" 
			class="scroll-h" 
			:scroll-x="true" 
			:show-scrollbar="false" 
			:scroll-into-view="scrollInto">
		    <view 
			  v-for="(tab, index) in subjectList"
			  :key="tab.id" 
			  class="uni-tab-item" 
			  :id="tab.id" 
			  :data-current="index" @click="ontabtap(tab, index)">
		        <text 
				  class="uni-tab-item-title" 
				 :class="tabIndex == index ? 'uni-tab-item-title-active' : ''">
				 {{tab.name}}
				</text>
		    </view>
		</scroll-view>
		
		<view class="line-h"></view>
	</view>
	
</template>

<script>
	import httpApi from '@/api/http'
	export default {
		props: {
			index: {
				type: Number,
				default: 0
			}
		},
		
		watch: {
			index (val) {
				this.tabIndex = val
			}
		}, 
		
		data () {
			return {
				scrollInto: '',
				tabIndex: 0,
				subjectList: []
			}
		},
		
		mounted () {
			this.getSubjectList()
		},
		
		methods: {
			getSubjectList () {
				httpApi.get('/student/subject')
				.then(res => {
					let result = res.data
					this.subjectList = result
					this.$emit('loadSuccess', result)
				})
			},
			
			ontabtap (subject, index) {
				this.tabIndex = index
				// this.scrollInto = subject.id
				this.$emit('click', subject, index)
			}
		}
	}
</script>

<style>
	.tabs {
	    flex: 1;
	    flex-direction: column;
	    overflow: hidden;
	    background-color: #ffffff;
	    /* #ifdef MP-ALIPAY || MP-BAIDU */
	    height: 100vh;
	    /* #endif */
	}
	
	.scroll-h {
	    width: 750rpx;
	    height: 80rpx;
	    flex-direction: row;
	    /* #ifndef APP-PLUS */
	    white-space: nowrap;
	    /* #endif */
	    /* flex-wrap: nowrap; */
	    /* border-color: #cccccc;
		border-bottom-style: solid;
		border-bottom-width: 1px; */
	}
	
	.line-h {
	    height: 1rpx;
	    background-color: #cccccc;
	}
	
	.uni-tab-item {
	    /* #ifndef APP-PLUS */
	    display: inline-block;
	    /* #endif */
	    flex-wrap: nowrap;
	    padding-left: 34rpx;
	    padding-right: 34rpx;
	}
	
	.uni-tab-item-title {
	    color: #555;
	    font-size: 30rpx;
	    height: 80rpx;
	    line-height: 80rpx;
	    flex-wrap: nowrap;
	    /* #ifndef APP-PLUS */
	    white-space: nowrap;
	    /* #endif */
	}
	
	.uni-tab-item-title-active {
	    color: #007AFF;
	}
</style>
