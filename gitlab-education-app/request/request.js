/**
 * 知识付费在线课程v2.0.0
 * Author: 山西匠言网络科技有限公司
 * This is not a free software, it under the license terms, you can visit https://www.zsffzxkc.cn/ get more details.
 */
const BASE_URL = uni.BASE_URL
export const HOST_URL = uni.HOST_URL

function request(options) {
	return new Promise((resolve, reject) => {
		
		uni.request({
			url: BASE_URL + options.url,
			method: options.method || 'GET',
			data: options.data || {},
			header: options.header || {},
			success: res => {
				resolve(res)
			},
			fail: (err) => {
				reject(err)
			},
			complete: () => {}
		});
		
		// // #ifdef H5
		// uni.request({
		// 	url: '/BASE_URL'+ options.url,
		// 	method: options.method || 'GET',
		// 	data: options.data || {},
		// 	header: options.header || {},
		// 	success: res => {
		// 		resolve(res)
		// 	},
		// 	fail: (err) => {
		// 		reject(err)
		// 	},
		// 	complete: () => {}
		// });
		// // #endif
	})
}
export default request
