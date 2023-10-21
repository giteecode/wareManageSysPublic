export default {
	
  namespaced: true,
  state: {
    wrongBook: null
  },
  
  getters: {
	  getWrongBook: state => {
		 if (state.wrongBook) {
		 	return state.wrongBook
		 }	
		 let wrongBook = uni.getStorageSync('wrongBook')
		 if (wrongBook) {
		 	return JSON.parse(wrongBook)
		 }
		 return null	
	  }
  },
  
  mutations: {
    updateWrongBook (state, wrongBook) {
		state.wrongBook = wrongBook
		uni.setStorage({
			key: 'wrongBook',
			data: JSON.stringify(wrongBook)
		})
	}
  }
}