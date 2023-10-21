import axios from 'axios'
import $httpApi from '@/api/index'
/**
 * 根据类型获取字典值
 * @param dictType
 * @param callBack
 */
export function getDictValueByType (dictType) {
  return axios.get($httpApi.httpUrl('/api/dictValue/selectByDictType'), {
    params: {
      dictType: dictType
    }
  })
}
