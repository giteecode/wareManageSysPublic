export default {
  namespaced: true,
  state: {
    courseInfo: null
  },
  mutations: {
    updateCourseInfo (state, courseInfo) {
      state.courseInfo = courseInfo
      sessionStorage.setItem('courseInfo', JSON.stringify(courseInfo))
    }
  },

  getters: {
    getCourseInfo (state) {
      if (state.courseInfo) {
        return state.courseInfo
      }
      return JSON.parse(sessionStorage.getItem('courseInfo'))
    }
  }
}
