/**
 * 知识付费在线课程v2.0.0
 * Author: 山西匠言网络科技有限公司
 * This is not a free software, it under the license terms, you can visit https://www.zsffzxkc.cn/ get more details.
 */
import request from '@/request/request.js'

// 获取讲师列表
export const getTutorList = () => {
	return request({
		url: 'index/teacher/index'
	})
}

// 获取讲师详情
export const postTutorDetail = (data) => {
	return request({
		url: 'index/teacher/detail',
		method: 'post',
		data
	})
}


// 讲师招募
export const postTeacherJsreg = (data) => {
	return request({
		url: 'index/teacher/js_reg',
		method: 'post',
		data
	})
}

// 发送验证码
export const postSendCode = (data) => {
	return request({
		url: 'index/teacher/sendcode',
		method: 'post',
		data
	})
}