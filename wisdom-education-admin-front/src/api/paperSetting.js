import axios from 'axios'
import $http from '@/utils/httpRequest'

export function savePaperSetting (params) {
  return axios.post($http.httpUrl('/system/testPaper/setting'), params)
}
