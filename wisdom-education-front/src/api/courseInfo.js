import axios from 'axios'
import $httpApi from '@/api/index'

/**
 * 获取课程详情
 * @param dictType
 * @param callBack
 */
export function getById (id) {
  return axios.get($httpApi.httpUrl('/student/courseInfo/' + id))
}
