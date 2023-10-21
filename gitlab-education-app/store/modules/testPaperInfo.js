export default {
	
  namespaced: true,
  state: {
    testPaperInfo: null
  },
  
  getters: {
	  getTestPaperInfo: state => {
		 if (state.testPaperInfo) {
		 	return state.testPaperInfo
		 }	
		 let testPaperInfo = uni.getStorageSync('testPaperInfo')
		 if (testPaperInfo) {
		 	return JSON.parse(testPaperInfo)
		 }
		 return null	
	  }
  },
  
  mutations: {
    updateTestPaperInfo (state, testPaperInfo) {
		state.testPaperInfo = testPaperInfo
		uni.setStorage({
			key: 'testPaperInfo',
			data: JSON.stringify(testPaperInfo)
		})
	}
  }
}