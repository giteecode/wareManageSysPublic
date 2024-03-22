import request from '@/utils/request'

// 查询库存操作统计
export function listStatsStockRecord(query) {
  return request({
    url: '/stats/stockRecord',
    method: 'get',
    params: query
  })
}
