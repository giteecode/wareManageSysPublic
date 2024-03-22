import request from '@/utils/request'

// 查询入库单退货列表
export function listInReturn(query) {
  return request({
    url: '/stock/inReturn/list',
    method: 'get',
    params: query
  })
}

// 查询入库单退货详细
export function getInReturn(returnId) {
  return request({
    url: '/stock/inReturn/' + returnId,
    method: 'get'
  })
}

// 新增入库单退货
export function addInReturn(data) {
  return request({
    url: '/stock/inReturn',
    method: 'post',
    data: data
  })
}

// 修改入库单退货
export function updateInReturn(data) {
  return request({
    url: '/stock/inReturn',
    method: 'put',
    data: data
  })
}

// 删除入库单退货
export function delInReturn(returnId) {
  return request({
    url: '/stock/inReturn/' + returnId,
    method: 'delete'
  })
}

// 打印入库退货单
export function printInOrderReturn(returnId) {
  return request({
    url: '/stock/inReturn/printInOrderReturn/' + returnId,
    method: 'get',
    responseType: 'arraybuffer',
    headers: {
      'Content-Type': 'application/json'
    },
  })
}