/**
 * 知识付费在线课程v2.0.0
 * Author: 山西匠言网络科技有限公司
 * This is not a free software, it under the license terms, you can visit https://www.zsffzxkc.cn/ get more details.
 */
export const commonShare = (res) => {
	let path = getCurrentPages()
	//console.log(path[0])
	let path_share = path[0].$page.fullPath
	let path_title = path[0].data.title
	console.log(encodeURI(path_title))
	console.log(path_share)
	let userinfo = uni.getStorageSync('userinfo')
	if (res.from === 'button') {
	    
	}
	return {
		title: decodeURI(path_title),
		path: `${path_share}?pid=${userinfo.uid}`,
		imageUrl:'../static/banner4.png'
	}
}