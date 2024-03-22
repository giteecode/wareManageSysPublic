import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false

// vuex
import store from './store'
Vue.prototype.$store = store

//http
import { http } from '@/util/http.js'
Vue.prototype.$http = http
//配置参数
import config from '@/common/config.js'
Vue.prototype.$config = config

// 引入全局uView
import uView from '@/uni_modules/uview-ui'
App.mpType = 'app'
Vue.use(uView)

const app = new Vue({
    store,
    ...App
})

// 引入请求封装
require('./util/request/index')(app)

app.$mount()
