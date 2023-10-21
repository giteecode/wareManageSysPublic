<template>
	<view 
		class="mk-upload" 
		:class="{'reverse': reverse}"
	>
		<!--进行flex占位-->
		<block v-if="imgList.length >= column && reverse">
			<block v-if="imgItemAppendLength === 0">
				<view
					class="mk-upload_item"
					:class="uploadItemColumn"
					v-for="(item, index) in (imgItemAppendLength + (column -1))"
				/>
			</block>
			<block v-if="imgItemAppendLength === 1">
				<block v-if="column == 3">
					<view
						class="mk-upload_item"
						:class="uploadItemColumn"
						v-for="(item, index) in imgItemAppendLength"
					/>
				</block>
				<block v-if="column == 4">
					<view
						class="mk-upload_item"
						:class="uploadItemColumn"
						v-for="(item, index) in (imgItemAppendLength + 1)"
					/>
				</block>
				<block v-if="column == 5">
					<view
						class="mk-upload_item"
						:class="uploadItemColumn"
						v-for="(item, index) in (imgItemAppendLength + 2)"
					/>
				</block>
			</block>
			<block v-if="imgItemAppendLength === 2">
				<block v-if="column == 4">
					<view
						class="mk-upload_item"
						:class="uploadItemColumn"
						v-for="(item, index) in (imgItemAppendLength - 1)"
					/>
				</block>
				<block v-if="column == 5">
					<view
						class="mk-upload_item"
						:class="uploadItemColumn"
						v-for="(item, index) in imgItemAppendLength"
					/>
				</block>
			</block>
			<block v-if="imgItemAppendLength === 3">
				<block v-if="column == 5">
					<view class="mk-upload_item" :class="uploadItemColumn"/>
				</block>
			</block>
		</block>
		<!--进行flex占位-->
		
		<view 
			class="mk-upload_item"
			:class="uploadItemColumn"
			v-for="(item, index) in imgList"
			:key="index"
		>
			<view 
				class="mk-upload_remove" 
				:class="deletePosition"
				@tap="onDeleteThis(index)"
				v-if="deleteShow"
			>
				<text>✕</text>
			</view>
			<image
				:src="item"
				class="mk-upload_item_img" 
				@tap="onPreviewImage(index)" 
				mode="aspectFill"
			/>
		</view>
		<!-- 列表end -->
		
		
		<view
			class="mk-upload_item"
			:class="uploadItemColumn"
			v-if="controlShow && imgList.length < maxCount"
		>
			<view 
				class="mk-upload_add"
				@tap="onChooseImage"
			>+</view>
		</view>
		<!-- 上传按钮 -->
		
			
	</view>
</template>

<script>
	export default {
		//组件传参
		props: {
			//图片数组
			imgList: {
				type: Array,
				default: function() {
				  return []
				}
			},
			//行数量
			column: {
				type: [Number, String],
				default: 3
			},
			reverse: {
				type: Boolean,
				default: false
			},
			//是否显示上传按钮
			controlShow: {
				type: Boolean,
				default: true
			},
			//是否显示删除按钮
			deleteShow: {
				type: Boolean,
				default: true
			},
			//删除按钮位置
			deletePosition: {
				type: String,
				default: 'topRight'
			},
			//album 从相册选图，camera 使用相机
			sourceType: {
				type: Array,
				default: function() {
					return ['camera', 'album']
				}
			},
			//original 原图，compressed 压缩图
			sizeType: {
				type: Array,
				default: function() {
					return ['original', 'compressed']
				}
			},
			//最多可以选择的图片张
			maxChooseCount: {
				type: Number,
				default: 9
			},
			//最大条数
			maxCount: {
				type: Number,
				default: 100
			}
		},
		data() {
			return {
			}
		},
		//计算属性
		computed: {
			uploadItemColumn(){
				return `mk-upload_item_column${this.column}`
			},
			imgItemAppendLength(){
				return this.imgList.length % this.column;
			}
		},
		//检测属性
		watch: {
		},
		//组件加载后
		created() {
		},
		methods: {
			//删除指定图片
			onDeleteThis(index){
				if(this.$listeners.onDeleteTake){
					this.$emit('onDeleteTake', index);
					return;
				}
				uni.showModal({
					title: '提示',
					content: '您确定删除吗？',
					success: (res)=> {
						if(res.confirm) {
							this.$emit('onDelete', index);
						}
					}
				});
			},
			//预览图片
			onPreviewImage(index) {
				if(this.$listeners.onPreviewTake){
					this.$emit('onPreviewTake', index);
					return;
				}
				const imgList = this.imgList;
				const isReverse = this.reverse;
				const currentIndex = isReverse ? (imgList.length - index) - 1 : index
				const imgLists = isReverse ? imgList.reverse() : imgList;
				uni.previewImage({
					current: currentIndex,
					urls: imgLists
				});
			},
			//选择图片
			onChooseImage(){
				uni.chooseImage({
					sizeType: this.sizeType,
					sourceType: this.sourceType,
					count: this.maxChooseCount,
					success: (res) => {
						this.$emit('onChoose', res);
					}
				});
			}
		}
	}
</script> 

<style lang="less" scoped>
	.mk-upload{
		
		display: flex;
		flex-wrap: wrap;
		box-sizing: border-box;
		padding-left: var(--mk-upload_item_margin, 15px);
		padding-bottom: var(--mk-upload_item_margin, 15px);
		flex-direction: row;
		&.reverse{
			flex-direction: row-reverse;
			flex-wrap: wrap-reverse;
			justify-content: flex-end;
		}
		
		.mk-upload_item{
			position: relative;
			margin-top: var(--mk-upload_item_margin, 15px);
			margin-right: var(--mk-upload_item_margin, 15px);
			flex: 0 calc(33.3% - var(--mk-upload_item_margin, 15px));
			.mk-upload_remove{
				background-color: var(--mk-upload_remove_color, #D1372C);
				color: #fff;
				width: var(--mk-upload_remove_size, 24px);
				height: var(--mk-upload_remove_size, 24px);
				display: flex;
				justify-content: center;
				align-items: flex-start;
				opacity: 0.8;
				position: absolute;
				z-index: 2;
				cursor: pointer;
				box-sizing: border-box;
				text{
					transform: scale(0.8);
					position: absolute;
					top: -2upx;
					line-height: var(--mk-upload_remove_size, 24px);
				}
				&:active{
					opacity: 0.6;
				}
				&.topLeft{
					top: 0;
					left: 0;
					border-radius: 0 0 100% 0;
					text{
						left: 8upx;
					}
				}
				&.topRight{
					top: 0;
					right: 0;
					border-radius: 0 0 0 100%;
					text{
						left: 18upx;
					}
				}
				&.bottomLeft{
					bottom: 0;
					left: 0;
					border-radius: 0 100% 0 0;
					text{
						left: 8upx;
						top: 6upx;
					}
				}
				&.bottomRight{
					bottom: 0;
					right: 0;
					border-radius: 100% 0 0 0;
					text{
						top: 6upx;
						left: 16upx;
					}
				}
			}
			.mk-upload_add{
				display: flex;
				justify-content: center;
				align-items: center;
				background-color: var(--mk-upload_add_bgcolor, #f1f1f1);
				font-size: var(--mk-upload_add_size, 30px);
				height: var(--mk-upload_img_height, 100px);
				width: 100%;
			}
			.mk-upload_item_img{
				display: block;
				width: 100%;
				height: var(--mk-upload_img_height, 100px);
			}
			&.mk-upload_item_column2{
				flex: 0 calc(50% - var(--mk-upload_item_margin, 15px));
			}
			&.mk-upload_item_column3{
				flex: 0 calc(33.3% - var(--mk-upload_item_margin, 15px));
			}
			&.mk-upload_item_column4{
				flex: 0 calc(25% - var(--mk-upload_item_margin, 15px));
				.mk-upload_item_img,
				.mk-upload_add{
					height: var(--mk-upload_img_height, 70px);
					font-size: var(--mk-upload_add_size, 25px);
				}
			}
			&.mk-upload_item_column5{
				flex: 0 calc(20% - var(--mk-upload_item_margin, 15px));
				.mk-upload_item_img,
				.mk-upload_add{
					height: var(--mk-upload_img_height, 55px);
					font-size: var(--mk-upload_add_size, 25px);
				}
			}
			&.mk-upload_item_column4,
			&.mk-upload_item_column5{
				.mk-upload_remove{
					width: var(--mk-upload_remove_size, 20px);
					height: var(--mk-upload_remove_size, 20px);
					text{
						transform: scale(0.6);
					}
					&.topLeft{
						text{
							left: 6upx;
							top: -6upx;
						}
					}
					&.topRight{
						text{
							left: 10upx;
							top: -6upx;
						}
					}
					&.bottomLeft{
						text{
							left: 4upx;
							top: 0;
						}
					}
					&.bottomRight{
						text{
							left: 10upx;
							top: 0;
						}
					}
				}
			}
			
		}
	}
</style>
