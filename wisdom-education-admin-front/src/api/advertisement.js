import axios from 'axios'
import $http from '@/utils/httpRequest'

/**
 * 广告列表
 * @param dictType
 * @param callBack
 */
export function listPage (params) {
  return axios.get($http.httpUrl('/system/advertisement'), {
    params: params
  })
}

/**
 * 添加或保存广告
 * @param params
 * @returns {AxiosPromise<any>}
 */
export function saveOrUpdate (params) {
  return axios.post($http.httpUrl('/system/advertisement'), params)
}
