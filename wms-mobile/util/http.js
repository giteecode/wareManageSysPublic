import store from "@/store/index.js"
import config from "@/common/config.js"

export const http = (options)=>{  
    return new Promise((resolve,reject)=>{
		let data = {
			_t: Date.parse(new Date()) / 1000,
			...options.data	
		};
		let header = {
			'content-type': (options.method === 'POST' ? 'application/json;charset=utf-8' : ''), 
		};
		let token = uni.getStorageSync("vuex_token");
		if(options.url !== '/mLogin'){
			header.Authorization = token;
		}
        uni.request({
            url: config.BASE_URL + options.url,
            method: options.method || 'GET',
            data: data || {},
			header: header,
            success: (res) => {
                if(res.statusCode !== 200){
                    return uni.showToast({
                        title: '获取数据失败！'
                    })
                }
                resolve(res.data)
            },
            fail: (err)=>{
                uni.showToast({
                    title: '系统繁忙！'
                })
                reject(err)
            }
        })
    })
}