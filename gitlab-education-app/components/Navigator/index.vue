<template name="Nav">
	<view class="nav-box bg-gradual-blue">
<!-- 		<view class="nav-foot">
			<text class="text-white cuIcon-footprint" @tap="goHistory"></text>
		</view> -->
		<view class="nav-search" @tap="goSearch">
			<text class="nav-search-icon text-gray cuIcon-search"></text>
		</view>
		<view class="nav-favor">
			<text class="text-white cuIcon-favor" @tap="goFavor"></text>
		</view>
	</view>
</template>

<script>
	export default {
		name: "Nav",
		props:{},
		methods:{
			goHistory(){
				uni.navigateTo({
					url: '../../pages/history/history'
				})
			},
			goSearch(){
				uni.navigateTo({
					url: '../../pages/search/search'
				})
			},
			goFavor(){
				uni.navigateTo({
					url: '../../pages/favor/favor'
				})
			}
			
		}
	}
</script>

<style>
	@import url('./index.css');
</style>
