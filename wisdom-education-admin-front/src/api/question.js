import axios from 'axios'
import $http from '@/utils/httpRequest'

/**
 * 试题详情
 * @param id
 * @returns {AxiosPromise<any>}
 */
export function getById (id) {
  return axios.get($http.httpUrl('/system/question/' + id))
}

export function getAnalysisById (id) {
  return axios.get($http.httpUrl('/system/question/' + id + '/analysis'))
}

export function questionAnswerListPage (id) {
  return axios.get($http.httpUrl('/system/question/' + id + '/answer'))
}
