import axios from 'axios'
import $http from '@/utils/httpRequest'

/**
 *  课程章节列表
 * @param dictType
 * @param callBack
 */
export function deleteSectionNodeById (courseId, id) {
  return axios.delete($http.httpUrl('/system/course/' + courseId + '/section/node/' + id))
}
