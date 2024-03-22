import request from '@/utils/request'

// 查询库存信息列表
export function listInfo(query) {
  return request({
    url: '/stock/info/list',
    method: 'get',
    params: query
  })
}

// 查询库存信息详细
export function getInfo(infoId) {
  return request({
    url: '/stock/info/' + infoId,
    method: 'get'
  })
}

// 新增库存信息
export function addInfo(data) {
  return request({
    url: '/stock/info',
    method: 'post',
    data: data
  })
}

// 修改库存信息
export function updateInfo(data) {
  return request({
    url: '/stock/info',
    method: 'put',
    data: data
  })
}

// 删除库存信息
export function delInfo(infoId) {
  return request({
    url: '/stock/info/' + infoId,
    method: 'delete'
  })
}

// 查询库存汇总列表
export function statsList(query) {
  return request({
    url: '/stock/info/statsList',
    method: 'get',
    params: query
  })
}