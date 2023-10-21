export default {
	
  namespaced: true,
  state: {
     host: 'http://127.0.0.1', // 'http://120.79.144.34:8003',
	 fileHost: 'https://edu-1306212169.cos.ap-nanjing.myqcloud.com'
  },
  
  getters: {
	  
	   host: state => {
	      return state.host
	   },
	   
	   fileHost: state => {
	      return state.fileHost
	   },
  },
  
  mutations: {

  }
}
