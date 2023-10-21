import axios from 'axios'
import router from '@/router'
// import merge from 'lodash/merge'
const http = axios.create({
  timeout: 1000 * 30,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  }
})

/**
 * 请求拦截
 */
http.interceptors.request.use(config => {
  config.headers['token'] = localStorage.getItem('token') // 请求头带上token
  return config
}, error => {
  return Promise.reject(error)
})

/**
 * 响应拦截
 */
http.interceptors.response.use(response => {
  if (response.data && response.data.code === 401) { // 401, token失效
    localStorage.removeItem('token')
    router.push({ name: 'login' })
  }
  return response
}, error => {
  return Promise.reject(error)
})

/**
 * 请求地址处理
 * @param {*} actionName action方法名称
 */
http.httpUrl = (actionName) => {
  // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
  return (process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl) + actionName
}

/**
 * 获取请求url
 * @param {*} actionName action方法名称
 */
http.getHost = function () {
  return (process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl)
}

http.getFileHost = function () {
  return (process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? process.env.FILE_HOST : window.SITE_CONFIG.fileUrl)
}

http.getWebSocketHost = function () {
  return (process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? process.env.WEB_SOCKET_HOST : window.SITE_CONFIG.webSocket)
}
export default http
