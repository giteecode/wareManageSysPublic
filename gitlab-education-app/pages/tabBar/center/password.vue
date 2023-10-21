<template>
  <view>
        <view class="modify">
          <view class="modify-password">
            <cmd-input 
			v-model="form.password" 
			type="password"
			 displayable maxlength="26"
			 placeholder="请输入原始密码"></cmd-input>
          </view>
		  
		  <view class="modify-password">
		    <cmd-input 
			v-model="form.newPassword" 
			type="password" maxlength="26" 
			placeholder="请输入新密码"></cmd-input>
		  </view>
		  
          <view class="modify-password">
            <cmd-input 
			 v-model="form.confirmPassword" 
			 type="password" maxlength="26"
			  placeholder="请再次确认新密码"></cmd-input>
          </view>
          <button class="btn-modify btn-modify-active" 
            hover-class="btn-modify-hover" @tap="updatePassword">提交</button>
        </view>
  </view>
</template>

<script>
  import cmdNavBar from "@/components/cmd-nav-bar/cmd-nav-bar.vue"
  import cmdInput from "@/components/cmd-input/cmd-input.vue"

  export default {
    components: {
      cmdNavBar,
      cmdInput
    },

    data() {
      return {
        form: {
          password: '',
          newPassword: '',
          confirmPassword: ''
        }
       
      };
    },

    watch: {
      
    },

    methods: {
      updatePassword () {
		  if (!this.form.password) {
			  uni.showToast({
			  	title: '请输入原始密码',
			  	icon: 'none',
			  	duration: 2000
			  });
			  return;
		  }
		  if (!this.form.newPassword) {
			  uni.showToast({
				title: '请输入新密码',
				icon: 'none',
				duration: 2000
			  });
			  return;
		  }
		  if (!this.form.confirmPassword) {
			  uni.showToast({
				title: '请输入确认密码',
				icon: 'none',
				duration: 2000
			  });
			  return;
		  }
		  this.$httpApi.post('/student/resetting/password', this.form)
		  .then(res => {
			  
			  uni.showToast({
			  	title: res.message
			  })
			  if (res.code === 1) {
				  uni.clearStorage()
				  setTimeout(() => {
					 uni.redirectTo({
						url: './login'
					 })
				  }, 200)
			  }
		  })
	  }
	}
  }
</script>

<style>
  .cmd-page-body-top {
	padding-top: 0px;
  }
  .modify {
    margin-top: 118upx;
    margin-right: 72upx;
    margin-left: 72upx;
  }

  .modify-phone {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    border-bottom: 2upx #dedede solid;
    margin-top: 118upx;
    margin-bottom: 40upx;
  }

  .modify-phone-getcode {
    color: #3F51B5;
    text-align: center;
    min-width: 140upx;
  }

  .modify-password,
  .modify-code {
    margin-bottom: 40upx;
    border-bottom: 2upx #dedede solid;
  }

  .btn-modify {
    margin-top: 100upx;
    border-radius: 50upx;
    font-size: 16px;
    color: #fff;
    background: linear-gradient(to right, #88a1f9, #9ac6ff);
  }

  .btn-modify-active {
    background: linear-gradient(to right, #365fff, #36bbff);
  }

  .btn-modify-hover {
    background: linear-gradient(to right, #365fdd, #36bbfa);
  }

  button[disabled] {
    color: #fff;
  }
</style>
