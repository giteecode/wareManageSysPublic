<template name="Nav">
	<view class="nav-box">
		<view class="nav-search" @tap="goSearch">
			<text class="nav-search-icon text-gray cuIcon-search"></text>
		</view>
	</view>
</template>

<script>
	export default {
		name: "course-navigator",
		props:{},
		methods:{
			goSearch(){
				uni.navigateTo({
					url: '../../pages/search/search'
				})
			},			
		}
	}
</script>

<style>
	@import url('./index.css');
</style>
