import axios from 'axios'
import $http from '@/utils/httpRequest'

/**
 * 获取阶段类型
 * @param callBack
 */
export function getSchoolTypeListApi (callBack) {
  let params = {
    'type': 'school_type'
  }
  sendHttp(params, callBack)
}

/**
 * 获取年级类型
 * @param callBack
 */
export function getGradeTypeListApi (callBack) {
  let params = {
    'type': 'grade_type'
  }
  sendHttp(params, callBack)
}

/**
 * 获取试题类型
 * @param callBack
 */
export function getQuestionTypeListApi (callBack) {
  let params = {
    'type': 'question_type'
  }
  sendHttp(params, callBack)
}

function sendHttp (params, callBack) {
  axios.get($http.httpUrl('/dict/getDictValueByType'), {
    params: {
      'type': params.type
    }
  }).then((response) => {
    callBack(response.data.data)
  })
}

/**
 * 根据parentId获取字典值
 * @param val
 * @param result
 */
export function getDictValueListByParentId (val, dictType, callback) {
  axios.get($http.httpUrl('/dict/getDictValueList'), {
    params: {
      type: dictType,
      parentId: val
    }
  }).then(function (response) {
    callback(response.data.data.dataList)
  }).catch(function (error) {
    console.log(error)
  })
}
