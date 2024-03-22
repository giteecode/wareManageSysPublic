import request from '@/utils/request'

// 查询物料主数据列表
export function listMat(query) {
  return request({
    url: '/base/mat/list',
    method: 'get',
    params: query
  })
}

// 查询物料主数据详细
export function getMat(matId) {
  return request({
    url: '/base/mat/' + matId,
    method: 'get'
  })
}

// 新增物料主数据
export function addMat(data) {
  return request({
    url: '/base/mat',
    method: 'post',
    data: data
  })
}

// 修改物料主数据
export function updateMat(data) {
  return request({
    url: '/base/mat',
    method: 'put',
    data: data
  })
}

// 删除物料主数据
export function delMat(matId) {
  return request({
    url: '/base/mat/' + matId,
    method: 'delete'
  })
}
