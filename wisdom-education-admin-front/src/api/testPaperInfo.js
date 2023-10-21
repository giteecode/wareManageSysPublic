import axios from 'axios'
import $http from '@/utils/httpRequest'

/**
 * 试卷列表
 * @param dictType
 * @param callBack
 */
export function getPaperInfoList (params) {
  return axios.get($http.httpUrl('/system/testPaperInfo'), {
    params: params
  })
}

/**
 * 试卷试题分析
 * @param params
 * @returns {AxiosPromise<any>}
 */
export function getPaperQuestionAnalysis (params) {
  return axios.get($http.httpUrl('/system/testPaperInfo/' + params.id + '/question/analysis'), {
    params: params
  })
}
