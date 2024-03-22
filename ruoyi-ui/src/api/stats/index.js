import request from '@/utils/request'

// 查询首页头部统计
export function statsIndexUpper(query) {
  return request({
    url: '/stats/indexUpper',
    method: 'get',
    params: query
  })
}

// 查询首页中部统计
export function statsIndexMiddle(query) {
  return request({
    url: '/stats/indexMiddle',
    method: 'get',
    params: query
  })
}

// 查询首页下部统计
export function statsIndexLower(query) {
  return request({
    url: '/stats/indexLower',
    method: 'get',
    params: query
  })
}
