const httpApi = {}
import store from '@/store'
// import request from './request.js'

httpApi.get = ((url, params) => {
    let baseUrl = store.state.common.host
    let tokenInfo = store.getters['user/getTokenInfo']
    let token = (tokenInfo !== null && tokenInfo.token) ? tokenInfo.token : null
    return new Promise(resolve => {
        uni.request({
            url: baseUrl + url,
            data: params,
            method: 'get',
            header: {
            	'Platform': 'educationStudent',
                'Authorization': token
            },
            success: (res) => {
				
				setToken(res)
				let result = res.data
			    if (result.code !== 1) {
				   if (result.code === 401) {
					   uni.redirectTo({
					       url: '/pages/tabBar/center/login'
					   })
				   }
			    } else {
				   resolve(result)
			    }
            },
		   
		    fail: (res) => {
			   console.log(res)
		    }
       })
    })
})

httpApi.upload = ((url, file) => {
    let baseUrl = store.state.common.host
    let tokenInfo = store.getters['user/getTokenInfo']
    let token = (tokenInfo !== null && tokenInfo.token) ? tokenInfo.token : null
    return new Promise(resolve => {
		uni.uploadFile({
			header: {
				'Platform': 'educationStudent',
			    'Authorization': token
			},
			url: baseUrl + url, //需传后台图片上传接口
			filePath: file[0],
			name: 'file',
			success: (res) => {
				setToken(res)
			   let result = JSON.parse(res.data)
			   if (result.code !== 1) {
				   if (result.code === 401) {
					   uni.redirectTo({
					       url: '/pages/tabBar/center/login'
					   })
				   }
			   } else {
				   resolve(result)
			   }
			},
					   
			fail: (res) => {
			   console.log(res)
			}
		})
    })
})

httpApi.post = ((url, params) => {
	let baseUrl = store.state.common.host
	let tokenInfo = store.getters['user/getTokenInfo']
	let token = (tokenInfo !== null && tokenInfo.token) ? tokenInfo.token : null
	return new Promise(resolve => {
		uni.request({
		    url: baseUrl + url, 
		    data: params,
			method: 'post',
		    header: {
				'Platform': 'educationStudent',
		        'Authorization': token
		    },
		    success: (res) => {
		    	
				setToken(res)
				let result = res.data
				if (result.code !== 1) {
				   if (result.code === 401) {
					  /* uni.showToast({
						  icon: 'none',
						  title: result.message
					   }) */
					   uni.redirectTo({
						   url: '/pages/userCenter/login'
					   })
				   } else if (result.code === 0) {
					   uni.showToast({
						   icon: 'none',
					   	  title: result.message
					   })
				   }
				} else {
					resolve(result)
				}
		    },
			fail: (res) => {
				
			}
		})
	}) 
})

export function setToken(res) {
	if (res.header && res.header.authorization) {
		let tokenInfo = {
			token: res.header.authorization,
			access_token: ''
		}
		store.commit('user/updateTokenInfo', tokenInfo)
	}

	if (res.header && res.header.Authorization) {
		let tokenInfo = {
			token: res.header.Authorization,
			access_token: ''
		}
		store.commit('user/updateTokenInfo', tokenInfo)
	}
}

export default httpApi