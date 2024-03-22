import request from '@/utils/request'

// 查询库存流水列表
export function listRecord(query) {
  return request({
    url: '/stock/record/list',
    method: 'get',
    params: query
  })
}

// 查询库存流水列表
export function returnListRecord(orderNo) {
  return request({
    url: '/stock/record/returnList/' + orderNo,
    method: 'get',
  })
}

// 查询库存流水详细
export function getRecord(recordId) {
  return request({
    url: '/stock/record/' + recordId,
    method: 'get'
  })
}

// 新增库存流水
export function addRecord(data) {
  return request({
    url: '/stock/record',
    method: 'post',
    data: data
  })
}

// 修改库存流水
export function updateRecord(data) {
  return request({
    url: '/stock/record',
    method: 'put',
    data: data
  })
}

// 删除库存流水
export function delRecord(recordId) {
  return request({
    url: '/stock/record/' + recordId,
    method: 'delete'
  })
}
