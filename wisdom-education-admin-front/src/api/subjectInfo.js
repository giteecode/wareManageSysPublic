import axios from 'axios'
import $http from '@/utils/httpRequest'

/**
 * 年级列表
 * @param dictType
 * @param callBack
 */
export function getSubjectInfoList (params) {
  return axios.get($http.httpUrl('/system/subject'), {
    params: params
  })
}
