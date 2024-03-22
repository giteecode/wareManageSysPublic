import request from '@/utils/request'

// 查询用户管理的物料分类列表
export function listClass(query) {
  return request({
    url: '/system/class/list',
    method: 'get',
    params: query
  })
}

// 修改用户管理的物料分类
export function changeActive(data) {
  return request({
    url: '/system/class/changeActive',
    method: 'put',
    data: data
  })
}
