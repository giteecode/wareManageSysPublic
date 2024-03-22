import request from '@/utils/request'

// 查询物料分类列表
export function listClass(query) {
  return request({
    url: '/base/class/list',
    method: 'get',
    params: query
  })
}

// 查询物料分类(所有)
export function listAllClass(query) {
  return request({
    url: '/base/class/listAll',
    method: 'get',
    params: query
  })
}

// 查询物料分类详细
export function getClass(classId) {
  return request({
    url: '/base/class/' + classId,
    method: 'get'
  })
}

// 新增物料分类
export function addClass(data) {
  return request({
    url: '/base/class',
    method: 'post',
    data: data
  })
}

// 修改物料分类
export function updateClass(data) {
  return request({
    url: '/base/class',
    method: 'put',
    data: data
  })
}

// 删除物料分类
export function delClass(classId) {
  return request({
    url: '/base/class/' + classId,
    method: 'delete'
  })
}
