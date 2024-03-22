import request from '@/utils/request'

// 查询入库单列表
export function listInOrder(query) {
  return request({
    url: '/stock/inOrder/list',
    method: 'get',
    params: query
  })
}

// 查询入库单详细
export function getInOrder(orderId) {
  return request({
    url: '/stock/inOrder/' + orderId,
    method: 'get'
  })
}

// 新增入库单
export function addInOrder(data) {
  return request({
    url: '/stock/inOrder',
    method: 'post',
    data: data
  })
}

// 修改入库单
export function updateInOrder(data) {
  return request({
    url: '/stock/inOrder',
    method: 'put',
    data: data
  })
}

// 删除入库单
export function delInOrder(orderId) {
  return request({
    url: '/stock/inOrder/' + orderId,
    method: 'delete'
  })
}

// 打印入库单
export function printInOrder(orderId) {
  return request({
    url: '/stock/inOrder/printInOrder/' + orderId,
    method: 'get',
    responseType: 'arraybuffer',
    headers: {
      'Content-Type': 'application/json'
    },
  })
}

// 质检
export function checkInOrder(data) {
  return request({
    url: '/stock/inOrder/check',
    method: 'put',
    data: data
  })
}
