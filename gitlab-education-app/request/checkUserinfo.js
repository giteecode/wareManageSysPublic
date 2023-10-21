/**
 * 知识付费在线课程v2.0.0
 * Author: 山西匠言网络科技有限公司
 * This is not a free software, it under the license terms, you can visit https://www.zsffzxkc.cn/ get more details.
 */
const BASE_URL = uni.BASE_URL
export const checkUserinfo=()=> {
	const userinfo_update = uni.getStorageSync('userinfo');
	const pid=uni.getStorageSync('pid')
	if (!userinfo_update || !userinfo_update.uid) {
		setTimeout(function(){
			uni.navigateTo({
				url: '/pages/login/login',
			});
		},1500)
	}else{
		uni.request({
			url: BASE_URL+'index/user/update_userdata',
			data: {
				uid:userinfo_update.uid,
				pid:pid?pid:0
			},
			method:'POST',
			success:(res) =>{
				let data=res.data.data
				userinfo_update.phone=data.phone
				userinfo_update.token=data.token
				uni.setStorageSync('userinfo',userinfo_update)
				
				const userinfo_check = uni.getStorageSync('userinfo');
				//console.log(userinfo_check)
				
				if(!userinfo_check.phone || userinfo_check.phone==''){
					uni.navigateTo({
						url: '/pages/login/login?bindphone=1',
					});
				}
				else{
					uni.request({
						url: BASE_URL+'index/user/check_user',
						data: {
							uid:userinfo_check.uid,
							phone:userinfo_check.phone,
							token:userinfo_check.token,
						},
						method:'POST',
						success:(res) =>{
							// back(res)
							console.log(res.data)
							if(res.data.code==200){
								const userdata = res.data.data
								userinfo_check.userdata=userdata
								uni.setStorageSync('userinfo',userinfo_check)
							}else{
								uni.showToast({
									title: res.data.msg,
									duration: 2000,
									icon:'none'
								});
								uni.removeStorageSync('userinfo');
								setTimeout(function(){
									uni.navigateTo({
										url: '/pages/login/login',
									});
								},1500)
							}
						},
						fail:(err)=> {
				
						}
					});
				}
			},
			fail:(err)=> {
		
			}
		});
	}
	}
	
	
	
	export const wx_login =()=>{
		var pid=uni.getStorageSync('pid')
		pid=pid?pid:0
		// #ifdef H5
		var ua = window.navigator.userAgent.toLowerCase();
		if (ua.match(/micromessenger/i) == 'micromessenger') {  
		   function GetQueryString(name) {
		     var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		     var r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
		     var context = ""; 
		     if (r != null) 
		   	 context = r[2]; 
		     reg = null; 
		     r = null; 
		     return context == null || context == "" || context == "undefined" ? "" : context; 
		   }
		   
		   var code1 = GetQueryString('code')
		   localStorage.setItem('code',code1)
		   var code = localStorage.getItem('code')
		   if (code == null || code === '') {
			   var H5_URL=window.location.href.split('#')[0]
			   //var H5_URL=uni.H5_URL
		   	window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx83c84f5f4ec1f158'+
		   	    '&redirect_uri=' + encodeURIComponent(H5_URL) + '&response_type=code&scope=snsapi_userinfo&state=#wechat_redirect'

		   }else{
		   	console.log(code)
			//window.location.href ='https://h5.sxjiangyan.com/#/'
		   	const BASE_URL=uni.BASE_URL
		   	uni.request({
		   		url: BASE_URL+'index/user/gzh_code',
		   		data: {
		   			code:code,
					pid:pid
		   		},
		   		method:'POST',
		   		success:(res) =>{
		   			console.log(res.data);
					var userinfo ={}
					userinfo.uid=res.data.data.uid
					userinfo.phone=res.data.data.phone
					userinfo.token=res.data.data.token
					userinfo.userdata=res.data.data
					uni.setStorageSync('userinfo',userinfo)
					// uni.switchTab({
					// 	url:'/pages/index/index'
					// })
		   		},
		   		fail:(res)=> {
		   			console.log(res.data);
		   		}
		   	});
		   }
		}
		// #endif
		// #ifdef MP-WEIXIN
			var apptype='MP-WEIXIN'
		const userinfo = uni.getStorageSync('userinfo');
		// 获取用户信息
		uni.getUserInfo({
		  provider: 'weixin',
		  success: function (infoRes) {
		    console.log(infoRes.userInfo);
			if(!userinfo || userinfo==''){
				var userinfo={}
				userinfo.wxinfo=infoRes.userInfo
			}else{
				userinfo.wxinfo=infoRes.userInfo
			}
			uni.setStorageSync('userinfo',userinfo)
			console.log(userinfo)
			
			uni.login({
			  provider: 'weixin',
			  success: function (loginRes) {
			    console.log(loginRes);
				const BASE_URL=uni.BASE_URL
				const code=loginRes.code
				uni.request({
					url: BASE_URL+'index/user/wxapp_code',
					data: {
						code:code,
						apptype:apptype
					},
					method:'POST',
					success:(res) =>{
						console.log(res.data);
						let openid=res.data.openid
						let unionid=res.data.unionid
						var userinfo=uni.getStorageSync('userinfo');
						uni.request({
							url: BASE_URL+'index/user/update_userinfo',
							data: {
								pid:pid,
								uid:userinfo.uid?userinfo.uid:'',
								avatar:userinfo.wxinfo.avatarUrl,
								unionid:unionid,
								openid:openid,
								nickname:userinfo.wxinfo.nickName,
								apptype:apptype
							},
							method:'POST',
							success:(res) =>{
								console.log(res.data);
								var userinfo=uni.getStorageSync('userinfo');
								userinfo.userdata=res.data.data
								userinfo.uid=res.data.data.uid
								userinfo.phone=res.data.data.phone
								userinfo.token=res.data.data.token
								uni.setStorageSync('userinfo',userinfo)
								uni.switchTab({
									url:'/pages/user/user'
								})
							},
							fail:(res)=> {
								console.log(res.data);
							}
						});
					},
					fail:(res)=> {
						console.log(res.data);
					}
				});
			
			  }
			});
		  }
		});

		// #endif
		// #ifdef APP-PLUS
			var apptype='APP-PLUS'
			const userinfo = uni.getStorageSync('userinfo');
			uni.login({
			  provider: 'weixin',
			  success: function (loginRes) {
			    console.log(loginRes);
				const BASE_URL=uni.BASE_URL
				uni.request({
					url: BASE_URL+'index/user/app_wxauth',
					data: {
						token:loginRes.authResult.access_token,
						openid:loginRes.authResult.openid,
						apptype:apptype,
						pid:pid
					},
					method:'POST',
					success:(res) =>{
						console.log(res.data);
						var userinfo ={}
						userinfo.uid=res.data.data.uid
						userinfo.phone=res.data.data.phone?res.data.data.phone:''
						userinfo.token=res.data.data.token
						userinfo.userdata=res.data.data
						uni.setStorageSync('userinfo',userinfo)
						uni.switchTab({
							url:'/pages/user/user'
						})
					},
					fail:(res)=> {
						console.log(res.data);
					}
				});
			
			  }
			});
			
		// #endif
	}