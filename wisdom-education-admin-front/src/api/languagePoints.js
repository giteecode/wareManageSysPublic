import axios from 'axios'
import $http from '@/utils/httpRequest'

/**
 * 年级列表
 * @param dictType
 * @param callBack
 */
export function selectByParentId (params) {
  return axios.get($http.httpUrl('/system/languagePointsInfo/' + params.parentId + '/children'))
}

/**
 * 获取顶级父节点
 * @param params
 * @returns {AxiosPromise<any>}
 */
export function selectFirstPoints (params) {
  params.parentId = 0
  return axios.get($http.httpUrl('/system/languagePointsInfo/first'), {
    params: params
  })
}
