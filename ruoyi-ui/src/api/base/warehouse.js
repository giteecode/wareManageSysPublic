import request from '@/utils/request'

// 查询仓库列表
export function listWarehouse(query) {
  return request({
    url: '/base/warehouse/list',
    method: 'get',
    params: query
  })
}

// 查询仓库列表(所有)
export function listAllWarehouse(query) {
  return request({
    url: '/base/warehouse/listAll',
    method: 'get',
    params: query
  })
}

// 查询仓库详细
export function getWarehouse(warehouseId) {
  return request({
    url: '/base/warehouse/' + warehouseId,
    method: 'get'
  })
}

// 新增仓库
export function addWarehouse(data) {
  return request({
    url: '/base/warehouse',
    method: 'post',
    data: data
  })
}

// 修改仓库
export function updateWarehouse(data) {
  return request({
    url: '/base/warehouse',
    method: 'put',
    data: data
  })
}

// 删除仓库
export function delWarehouse(warehouseId) {
  return request({
    url: '/base/warehouse/' + warehouseId,
    method: 'delete'
  })
}

// 打印仓库
export function printWarehouse(warehouseId) {
  return request({
    url: '/base/warehouse/printWarehouse/' + warehouseId,
    method: 'get',
    responseType: 'arraybuffer',
    headers: {
      'Content-Type': 'application/json'
    },
  })
}