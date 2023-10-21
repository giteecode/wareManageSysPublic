import axios from 'axios'
import $http from '@/api/index'
import router from '@/router/index'
import ElementUI from "element-ui";


export function clearLoginInfo () {
 // localStorage.removeItem('front-token')
 // localStorage.removeItem('userInfo')
  localStorage.clear()
  sessionStorage.clear()
}

export function goBack () {
  router.go(-1)
}

export function playVideo (url, viewName) {
  if (!url) {
    ElementUI.Message ({
      message: '该试题暂未提供教学视频,敬请期待！',
      type: 'warning'
    })
    return
  }
  if (url.lastIndexOf(',') !== -1) {
    url = url.substr(0, url.length - 1)
  }
  let reg = new RegExp('[\\u4E00-\\u9FFF]+', 'g');
  let newUrl = ''
  if (reg.test(url)) { //判断视频url是否含有中文
    // 获取字符串中的汉字
    let chinese = url.match(reg);
    let chineseStr = chinese.join("")
    let chineseToEnglish = encodeURI(chineseStr)
    let offset = url.lastIndexOf('/')
    let startUrl = url.substr(0, offset + 1)
    let suffix = url.substr(url.lastIndexOf('.'), url.length)
    newUrl = startUrl + chineseToEnglish + suffix
  } else {
    newUrl = url
  }
  this.$store.commit('common/updateVideoUrl', newUrl)

  if (viewName) {
    router.push({
      name: viewName
    })
  } else {
    router.push({
      name: 'video',
    })
  }
}


export function getQuestionTypeApi (callback) {
  let questionTypeList = JSON.parse(sessionStorage.getItem('question_type'))
  if (questionTypeList) {
    callback(questionTypeList)
  } else {
    axios.get($http.httpUrl('/student/dict'), {
      params: {
        type: 'question_type'
      }
    }).then(function (response) {
      sessionStorage.setItem('question_type', JSON.stringify(response.data.data.dataList))
      callback(response.data.data.dataList)
    }).catch(function (error) {
      console.log(error)
    })
  }
}
