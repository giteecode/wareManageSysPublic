/**
 * 知识付费在线课程v2.0.0
 * Author: 山西匠言网络科技有限公司
 * This is not a free software, it under the license terms, you can visit https://www.zsffzxkc.cn/ get more details.
 */
import request from '@/request/request.js'


// 系统消息
export const postNewnotice = (data) => {
	return request({
		url: 'index/notice/newnotice',
		method: 'post',
		data
	})
}