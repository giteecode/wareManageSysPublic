import Vue from 'vue'
import Vuex from 'vuex'
import common from './modules/common'
import user from './modules/user'
import testPaperInfo from './modules/testPaperInfo'
import courseInfo from './modules/course'
import wrongBook from './modules/wrongBook'
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    common,
	testPaperInfo,
	user,
	courseInfo,
	wrongBook
  },
  
  mutations: {
  
  },
})
