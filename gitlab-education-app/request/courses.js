/**
 * 知识付费在线课程v2.0.0
 * Author: 山西匠言网络科技有限公司
 * This is not a free software, it under the license terms, you can visit https://www.zsffzxkc.cn/ get more details.
 */
import request from './request.js'

// 课程详情 details=详情
export const getCourseDetails = (data) => {
	return request({
		url: 'index/courses/index',
		method: 'POST',
		data
	})
}


// 获取子分类课程列表
// export const postSonflList = (data) => {
// 	return request({
// 		url:'index/classify/sonfl_list',
// 		method: 'post',
// 		data
// 	})
// }


// 我的课程
export const postMyCourse = (data) => {
	return request({
		url: 'index/user/my_course',
		method: 'post',
		data
	})
}


// 我的收藏
export const postMyDingyue = (data) => {
	return request({
		url: 'index/user/my_dingyue',
		method: 'post',
		data
	})
}


// 课程评价
export const postCourseComment = (data) => {
	return request({
		url: 'index/courses/comment',
		method: 'post',
		data
	})
}