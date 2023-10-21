import axios from 'axios'
import $http from '@/utils/httpRequest'

/**
 * 根据类型获取字典值
 * @param dictType
 * @param callBack
 */
export function getDictValueByType (dictType) {
  return axios.get($http.httpUrl('/api/dictValue/selectByDictType'), {
    params: {
      dictType: dictType
    }
  })
}
