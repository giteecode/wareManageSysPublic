/**
 * 知识付费在线课程v2.0.0
 * Author: 山西匠言网络科技有限公司
 * This is not a free software, it under the license terms, you can visit https://www.zsffzxkc.cn/ get more details.
 */
import request from './request'


// 首页
export const getIndexInfo = () => {
	return request({
		url: 'index'
	})
}
// 首页更多推荐课程
export const getMoretjCourse = () => {
	return request({
		url: 'index/index/indextjlist'
	})
}
// 导航分类换一批
export const postDhHyp = (data) => {
	return request({
		url: 'index/index/dhhyp',
		method: 'post',
		data
	})
}
// 分类列表接口
export const postClassifylist = (data) => {
	return request({
		url: 'index/classify/datalist',
		method: 'post',
		data
	})
}

// 子分类列表接口
export const postSonflData = (data) => {
	return request({
		url: 'index/classify/sonfldata',
		method: 'post',
		data
	})
}



// 课程接口
export const getIndexListInfo = (data) => {
	return request({
		url: 'index/classify/index',
		method: 'POST',
		data
	})
}


// 课程一级目录课程列表
export const postIndexGoods = (data) => {
	return request({
		url: 'index/courses/indexgoods',
		method: 'post',
		data
	})
}

