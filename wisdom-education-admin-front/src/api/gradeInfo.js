import axios from 'axios'
import $http from '@/utils/httpRequest'

/**
 * 年级列表
 * @param dictType
 * @param callBack
 */
export function getGradeInfoList (params) {
  return axios.get($http.httpUrl('/system/gradeInfo'), {
    params: params
  })
}
