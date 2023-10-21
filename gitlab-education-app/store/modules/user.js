export default {
	
  namespaced: true,
  state: {
	 tokenInfo: {
		token: '',
		access_token: ''
	 },
	 
	 studentInfo: null
  },
  
  getters: {
	  
    getTokenInfo: state => {
	   if (state.tokenInfo.token) {
		   return state.tokenInfo
	   }	
	   let token = uni.getStorageSync('tokenInfo')
	   if (token) {
		   return JSON.parse(token)
	   }
       return null	   
    },
	
	getStudentInfo: state => {
	   if (state.studentInfo) {
	   		return state.studentInfo
	   }	
	   let studentInfo = uni.getStorageSync('studentInfo')
	   if (studentInfo) {
	   	  return JSON.parse(studentInfo)
	   }
	   return null	   
	}
	  
  },
  
  mutations: {
	  
	updateTokenInfo (state, tokenInfo) {
		state.tokenInfo = tokenInfo
		uni.setStorage({
			key: 'tokenInfo',
			data: JSON.stringify(tokenInfo)
		})
	},
	
	updateStudentInfo (state, studentInfo) {
		state.studentInfo = studentInfo
		uni.setStorage({
			key: 'studentInfo',
			data: JSON.stringify(studentInfo)
		})
	}
  }
}
