import request from '@/utils/request'

// 查询生产订单列表
export function listProdOrder(query) {
  return request({
    url: '/stock/prodOrder/list',
    method: 'get',
    params: query
  })
}

// 查询生产订单详细
export function getProdOrder(orderId) {
  return request({
    url: '/stock/prodOrder/' + orderId,
    method: 'get'
  })
}

// 新增生产订单
export function addProdOrder(data) {
  return request({
    url: '/stock/prodOrder',
    method: 'post',
    data: data
  })
}

// 修改生产订单
export function updateProdOrder(data) {
  return request({
    url: '/stock/prodOrder',
    method: 'put',
    data: data
  })
}

// 删除生产订单
export function delProdOrder(orderId) {
  return request({
    url: '/stock/prodOrder/' + orderId,
    method: 'delete'
  })
}
