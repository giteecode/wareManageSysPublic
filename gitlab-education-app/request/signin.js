/**
 * 知识付费在线课程v2.0.0
 * Author: 山西匠言网络科技有限公司
 * This is not a free software, it under the license terms, you can visit https://www.zsffzxkc.cn/ get more details.
 */
import request from '@/request/request.js'

// 签到页
export const postDaka = (data) => {
	return request({
		url: 'index/user/daka',
		method: 'post',
		data
	})
}
export const ljqd = (data) => {
	return request({
		url: 'index/user/daka_jifen',
		method: 'post',
		data
	})
}