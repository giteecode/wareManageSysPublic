import axios from 'axios'
import $http from '@/utils/httpRequest'

/**
 *  课程章节列表
 * @param dictType
 * @param callBack
 */
export function getCourseSectionList (courseId) {
  return axios.get($http.httpUrl('/system/course/' + courseId + '/section'))
}
