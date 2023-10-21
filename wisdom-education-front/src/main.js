// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from '@/store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import './assets/css/common.css'
import httpApi from '@/api/index'
import moment from 'moment'// 导入moment
// 引入v-charts
import VCharts from 'v-charts'
Vue.use(VCharts)
import echarts from 'echarts'
Vue.prototype.$echarts = echarts
import VueLazyload from 'vue-lazyload'

Vue.use(VueLazyload,{
  preLoad: 1.3,
  error: '/static/image/error.png',
  loading: '/static/image/loading.gif',
  attempt: 1
})

Vue.prototype.moment = moment
Vue.use(ElementUI);
Vue.config.productionTip = false
Vue.prototype.axios = axios
Vue.prototype.$httpApi = httpApi

import VideoPlayer from 'vue-video-player'
window.videojs = VideoPlayer.videojs

require('vue-video-player/src/custom-theme.css')
require('video.js/dist/video-js.min.css')
require('video.js/dist/lang/zh-CN.js')
Vue.use(VideoPlayer)

import {playVideo, goBack} from '@/api/common'
Vue.prototype.playVideo = playVideo
Vue.prototype.goBack = goBack
axios.interceptors.request.use(function (config) {
  let token = localStorage.getItem('token')
  config.headers.Platform = 'educationStudent'
  if (token) {
    // 这里将token设置到headers中，header的key是token，这个key值根据你的需要进行修改即可
    config.headers.Authorization = token
    //config.headers.httpType = 'student-product'
  }
  return config
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error)
})

// 添加全局响应拦截器
axios.interceptors.response.use(function (response) {
  let authorization = response.data.authorization
  if (authorization) {
    localStorage.setItem('token', authorization)
  }
  if (response.data.code !== 1) {
    // 对响应数据做点什么
    if (response.data.code === 401) {
      localStorage.clear()
      sessionStorage.clear()
      ElementUI.Message.error('会话已过期,请重新登录')
      router.push('/login')
    } else if (response.data.code === 0) {
        ElementUI.Message.error(response.data.message)
        return false
    }
  }
  return response
}, function (error) {
  return Promise.reject(error)
})


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
