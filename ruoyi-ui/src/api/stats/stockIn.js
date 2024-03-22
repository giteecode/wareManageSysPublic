import request from '@/utils/request'

// 查询入库统计
export function listStatsStockIn(query) {
  return request({
    url: '/stats/stockIn',
    method: 'get',
    params: query
  })
}
