import request from '@/utils/request'

// 查询入库统计
export function listStatsStockOut(query) {
  return request({
    url: '/stats/stockOut',
    method: 'get',
    params: query
  })
}
