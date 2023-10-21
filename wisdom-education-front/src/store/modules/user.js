export default {
  namespaced: true,
  state: {
    token: null,
    userInfo: null
  },
  mutations: {
    updateToken (state, token) {
      state.token = token
      localStorage.setItem('token', token)
    },

    updateUserInfo (state, userInfo) {
      state.userInfo = userInfo
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
    }
  },

  getters: {
    getToken (state) {
      if (state.token) {
        return state.token
      }
      return localStorage.getItem('token')
    },

    getUserInfo (state) {
      if (state.userInfo) {
        return state.userInfo
      }
      return JSON.parse(localStorage.getItem('userInfo'))
    }
  }
}
