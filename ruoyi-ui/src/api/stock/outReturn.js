import request from '@/utils/request'

// 查询出库单退货列表
export function listOutReturn(query) {
  return request({
    url: '/stock/outReturn/list',
    method: 'get',
    params: query
  })
}

// 查询出库单退货详细
export function getOutReturn(returnId) {
  return request({
    url: '/stock/outReturn/' + returnId,
    method: 'get'
  })
}

// 新增出库单退货
export function addOutReturn(data) {
  return request({
    url: '/stock/outReturn',
    method: 'post',
    data: data
  })
}

// 修改出库单退货
export function updateOutReturn(data) {
  return request({
    url: '/stock/outReturn',
    method: 'put',
    data: data
  })
}

// 删除出库单退货
export function delOutReturn(returnId) {
  return request({
    url: '/stock/outReturn/' + returnId,
    method: 'delete'
  })
}

// 打印出库退货单
export function printOutOrderReturn(returnId) {
  return request({
    url: '/stock/outReturn/printOutOrderReturn/' + returnId,
    method: 'get',
    responseType: 'arraybuffer',
    headers: {
      'Content-Type': 'application/json'
    },
  })
}