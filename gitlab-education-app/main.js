import Vue from 'vue'
import App from './App'
import store from './store'
import { hasLogin } from '@/api'
Vue.config.productionTip = false
Vue.prototype.$store = store
Vue.prototype.hasLogin = hasLogin
import httpApi from '@/api/http'
App.mpType = 'app'
Vue.prototype.$httpApi = httpApi
import pageHead from './components/page-head.vue'

Vue.component('page-head', pageHead)


/* import VConsole from '@/api/vconsole.min.js'
 
const vConsole = new VConsole() 
Vue.use(vConsole) */
const app = new Vue({
	store,
    ...App
})
app.$mount()
