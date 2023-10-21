import axios from 'axios'
import $http from '@/utils/httpRequest'

export function getSchoolList (callBack) {
  axios.get($http.httpUrl('/system/school'), {
    params: {

    }
  }).then(function (response) {
    callBack(response.data.data.dataList)
  })
}

/**
 * 根据年级获取科目
 * @param val
 * @param result
 */
export function changeGradeTypeApi (val, callback) {
  axios.get($http.httpUrl('/system/subject'), {
    params: {
   //   sqlId: 'system.subject.list',
      gradeType: val
    }
  }).then(function (response) {
    callback(response.data.data.dataList)
  }).catch(function (error) {
    console.log(error)
  })
}

/**
 * 获取年级类型列表
 * @param callback
 */
export function getGradeType (callback) {
  if (localStorage.getItem('grade_type')) {
    let gradeTypeList = JSON.parse(localStorage.getItem('grade_type'))
    callback(gradeTypeList)
  } else {
    axios.get($http.httpUrl('/system/dict'), {
      params: {
        'type': 'grade_type'
      }
    }).then(function (response) {
      localStorage.setItem('grade_type', JSON.stringify(response.data.data.dataList))
      callback(response.data.data.dataList)
    }).catch(function (error) {
      console.log(error)
    })
  }
}

/**
 * 获取学校类型列表
 * @param callback
 */
export function getSchoolType (callback) {
  let schoolTypeList = JSON.parse(localStorage.getItem('school_type'))
  if (schoolTypeList) {
    callback(schoolTypeList)
  } else {
    axios.get($http.httpUrl('/system/dict'), {
      params: {
        'type': 'school_type'
      }
    }).then(function (response) {
      localStorage.setItem('school_type', JSON.stringify(response.data.data.dataList))
      callback(response.data.data.dataList)
    }).catch(function (error) {
      console.log(error)
    })
  }
}

/**
 * 获取试题类型列表
 * @param callback
 */
export function getQuestionTypeApi (callback) {
  axios.get($http.httpUrl('/system/dict'), {
    params: {
      type: 'question_type'
    }
  }).then(function (response) {
    callback(response.data.data.dataList)
  }).catch(function (error) {
    console.log(error)
  })
}

export function getQuestionCategoryApi (callback) {
  axios.get($http.httpUrl('/system/dict'), {
    params: {
      type: 'question_category'
    }
  }).then(function (response) {
    callback(response.data.data.dataList)
  }).catch(function (error) {
    console.log(error)
  })
}

export function getCourseTypeApi (callback) {
  let courseTypeList = sessionStorage.getItem('course_type')
  if (!courseTypeList) {
    axios.get($http.httpUrl('/system/dict'), {
      params: {
        type: 'course_type'
      }
    }).then(function (response) {
      sessionStorage.setItem('course_type', response.data.data)
      callback(response.data.data)
    }).catch(function (error) {
      console.log(error)
    })
  } else {
    callback(JSON.parse(courseTypeList))
  }
}

export function getCourseList (callback) {
  axios.get($http.httpUrl('/system/course'), {
    params: {
      sqlId: 'course.info.list'
    }
  }).then(function (response) {
    callback(response.data.data)
  }).catch(function (error) {
    console.log(error)
  })
}

export function getSubjectList (callback) {
  axios.get($http.httpUrl('/system/subject/list'), {
    params: {
      sqlId: 'system.subject.list'
    }
  }).then(function (response) {
    callback(response.data.data.dataList)
  }).catch(function (error) {
    console.log(error)
  })
}

export function getModeType (callback) {
  axios.get($http.httpUrl('/api/pagination'), {
    params: {
      sqlId: 'system.dict.get',
      type: 'mode_type'
    }
  }).then(function (response) {
    callback(response.data.data.dataList)
  }).catch(function (error) {
    console.log(error)
  })
}
