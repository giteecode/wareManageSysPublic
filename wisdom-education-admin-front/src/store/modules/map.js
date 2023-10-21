export default {
  namespaced: true,
  state: {
    course: null,
    subjectInfo: null,
    testPaperInfoId: null,
    dictId: null,
    positionInfo: {},
    schoolForm: {},
    paperQuestionParams: {},
    studentExamInfo: {},
    testPaperInfo: null,
    positionList: []
  },

  getters: {

    getPaperQuestionParams (state) {
      if (state.paperQuestionParams.id) {
        return state.paperQuestionParams
      }
      return JSON.parse(sessionStorage.getItem('paperQuestionParams'))
    },

    getPositionInfo (state) {
      if (state.positionInfo.lng) {
        return state.positionInfo
      }
      return JSON.parse(sessionStorage.getItem('positionInfo'))
    },

    getTestPaperInfoId (state) {
      if (state.testPaperInfoId) {
        return state.testPaperInfoId
      }
      return JSON.parse(sessionStorage.getItem('testPaperInfoId'))
    },

    getSubjectInfo (state) {
      if (state.subjectInfo) {
        return state.subjectInfo
      }
      return JSON.parse(sessionStorage.getItem('subjectInfo'))
    },

    getStudentExamInfo (state) {
      if (state.studentExamInfo.paper_name) {
        return state.studentExamInfo
      }
      return JSON.parse(sessionStorage.getItem('studentExamInfo'))
    },

    getTestPaperInfo (state) {
      if (state.testPaperInfo) {
        return state.testPaperInfo
      }
      return JSON.parse(sessionStorage.getItem('testPaperInfo'))
    },

    getDictId (state) {
      if (state.dictId) {
        return state.dictId
      }
      return JSON.parse(sessionStorage.getItem('dictId'))
    },

    getCourse (state) {
      if (state.course) {
        return state.course
      }
      return JSON.parse(sessionStorage.getItem('course'))
    }
  },

  mutations: {

    updateStudentExamInfo (state, studentExamInfo) {
      sessionStorage.setItem('studentExamInfo', JSON.stringify(studentExamInfo))
      state.studentExamInfo = studentExamInfo
    },

    updateSubjectInfo (state, subjectInfo) {
      sessionStorage.setItem('subjectInfo', JSON.stringify(subjectInfo))
      state.subjectInfo = subjectInfo
    },

    updateCourse (state, course) {
      sessionStorage.setItem('course', JSON.stringify(course))
      state.course = course
    },

    updateTestPaperInfoId (state, testPaperInfoId) {
      sessionStorage.setItem('testPaperInfoId', testPaperInfoId)
      state.testPaperInfoId = testPaperInfoId
    },

    updatePositionInfo (state, positionInfo) {
      sessionStorage.setItem('positionInfo', JSON.stringify(positionInfo))
      state.positionInfo = positionInfo
    },

    updatePaperQuestionParams (state, paperQuestionParams) {
      sessionStorage.setItem('paperQuestionParams', JSON.stringify(paperQuestionParams))
      state.paperQuestionParams = paperQuestionParams
    },

    updatePositionList (state, positionList) {
      state.positionList = positionList
    },

    updateSchoolForm (state, form) {
      state.schoolForm = form
    },

    updateTestPaperInfo (state, testPaperInfo) {
      sessionStorage.setItem('testPaperInfo', JSON.stringify(testPaperInfo))
      state.testPaperInfo = testPaperInfo
    },

    updateDictId (state, dictId) {
      sessionStorage.setItem('dictId', dictId)
      state.dictId = dictId
    }
  }
}
