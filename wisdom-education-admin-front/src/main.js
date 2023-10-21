import Vue from 'vue'
import App from '@/App'
import router from '@/router'                 // api: https://github.com/vuejs/vue-router
import store from '@/store'                   // api: https://github.com/vuejs/vuex
import VueCookie from 'vue-cookie'            // api: https://github.com/alfhen/vue-cookie
import ElementUI from 'element-ui'
import '@/icons'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/scss/index.scss'
import httpRequest from '@/utils/httpRequest' // api: https://github.com/axios/axios
import axios from 'axios'
import { hasPermission } from '@/utils'
Vue.use(ElementUI)
// eslint-disable-next-line import/first
import cloneDeep from 'lodash/cloneDeep'
// eslint-disable-next-line import/first
import moment from 'moment'// 导入moment

// eslint-disable-next-line import/first

// eslint-disable-next-line import/first,import/first
import 'xe-utils'
// eslint-disable-next-line import/first
import VXETable from 'vxe-table'
// eslint-disable-next-line import/first
import 'vxe-table/lib/style.css'
// eslint-disable-next-line import/first
import {md5, encrypt} from './utils/aes'
Vue.use(VXETable)

Vue.prototype.moment = moment

Vue.use(VueCookie)
Vue.prototype.axios = axios
Vue.config.productionTip = false
Vue.prototype.hasPermission = hasPermission

axios.interceptors.request.use(function (config) {
  config.headers.Platform = 'educationAdmin'
  let token = localStorage.getItem('Authorization')
  if (token) {
    // 这里将token设置到headers中，header的key是token，这个key值根据你的需要进行修改即可
    config.headers.Authorization = token
  }

  let urlParam = config.params
  let data = config.data
  if (urlParam) {
    let aesParam = JSON.stringify(urlParam)
    config.headers.Sign = md5(encrypt(aesParam))
  } else if (data) {
    let jsonData = JSON.stringify(data)
    config.headers.Sign = md5(encrypt(jsonData))
  } else {
    config.headers.Sign = md5(encrypt(JSON.stringify({})))
  }
  return config
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error)
})

// 添加全局响应拦截器
axios.interceptors.response.use(function (response) {
  let authorization = response.headers.authorization
  if (authorization) {
    localStorage.setItem('Authorization', authorization)
  }
  console.log(response)
  // 对响应数据做点什么
  if (response.data.code === 401) {
    localStorage.clear()
    router.push('/login')
  } else if (response.data.code === 406) {
    ElementUI.Message({
      message: '权限不足，无法访问',
      type: 'error'
    })
    return false
  } else if (response.data.code === 0 || response.data.code === 402) {
   // console.log(response.data)
    ElementUI.Message({
      message: response.data.message,
      type: 'error'
    })
    return false
  }
  return response
}, function (error) {
  return Promise.reject(error)
})

// 挂载全局
Vue.prototype.$http = httpRequest // ajax请求方法
// Vue.prototype.isAuth = isAuth     // 权限方法

// 保存整站vuex本地储存初始状态
window.SITE_CONFIG['storeState'] = cloneDeep(store.state)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
