import request from '@/utils/request'

// 查询调拨单列表
export function listAllotOrder(query) {
  return request({
    url: '/stock/allotOrder/list',
    method: 'get',
    params: query
  })
}

// 查询调拨单详细
export function getAllotOrder(allotId) {
  return request({
    url: '/stock/allotOrder/' + allotId,
    method: 'get'
  })
}

// 新增调拨单
export function addAllotOrder(data) {
  return request({
    url: '/stock/allotOrder',
    method: 'post',
    data: data
  })
}

// 修改调拨单
export function updateAllotOrder(data) {
  return request({
    url: '/stock/allotOrder',
    method: 'put',
    data: data
  })
}

// 删除调拨单
export function delAllotOrder(allotId) {
  return request({
    url: '/stock/allotOrder/' + allotId,
    method: 'delete'
  })
}

// 打印调拨单
export function printAllotOrder(allotId) {
  return request({
    url: '/stock/allotOrder/printAllotOrder/' + allotId,
    method: 'get',
    responseType: 'arraybuffer',
    headers: {
      'Content-Type': 'application/json'
    },
  })
}