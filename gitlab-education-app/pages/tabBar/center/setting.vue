<template>
  <view style="background: #FFFFFF;">
   
        <view>
          <cmd-cel-item title="登录密码" @click="goPassword" arrow></cmd-cel-item>
        </view>
		
		<view>
		  <cmd-cel-item title="帮助中心" arrow></cmd-cel-item>
		</view>
  </view>
</template>

<script>
  import cmdTransition from "@/components/cmd-transition/cmd-transition.vue"
  import cmdCelItem from "@/components/cmd-cell-item/cmd-cell-item.vue"

  export default {
    components: {
      cmdTransition,
      cmdCelItem
    },

    data() {
		 
      return {
		  index: 0,
		  sex: [{
		  	id: 1,
		  	name: '男'
		  }, {
		  	id: 2,
		  	name: '女'
		  }]
	  };
    },

    mounted() {},
    
    methods:{
		
		bindPickerChange () {
			
		},
		
		switchName () {
			
		},
		
		uploadUserImage () {
			uni.chooseImage({
				count: 1,
				sizeType: ['original', 'compressed'],
				sourceType: ['album', 'camera'],
				success(res) {
				
				}
			})
		},
      /**
       * 点击触发
       * @param {Object} type 跳转页面名或者类型方式
       */
        goPassword(type){
            uni.navigateTo({
             url:'./password'
            })
        }
    }
  }
</script>

<style>
  .cmd-page-body-top {
	  padding-top: 0px;
  }
  .btn-logout {
    margin-top: 100upx;
    width: 80%;
    border-radius: 50upx;
    font-size: 16px;
    color: #fff;
    background: linear-gradient(to right, #365fff, #36bbff);
  }

  .btn-logout-hover {
    background: linear-gradient(to right, #365fdd, #36bbfa);
  }
</style>
