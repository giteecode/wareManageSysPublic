export default {
  namespaced: true,
  state: {
    webSiteConfig: null,
    videoUrl: null,
    testPaperInfo: null,
    testPaperId: null,
    sectionNodeInfo: null,
    webSocketHost: process.env.WEBSOCKET_HOST,
    fileHost: process.env.FILE_HOST,
    menuIndex: null
  },

  mutations: {

    updateWebSiteConfig (state, webSiteConfig) {
      state.webSiteConfig = webSiteConfig
      sessionStorage.setItem('webSiteConfig', JSON.stringify(webSiteConfig))
    },

    updateTestPaperInfo (state, testPaperInfo) {
      state.testPaperInfo = testPaperInfo
      sessionStorage.setItem('testPaperInfo', JSON.stringify(testPaperInfo))
    },

    updateTestPaperId (state, testPaperId) {
      state.testPaperId = testPaperId
      sessionStorage.setItem('testPaperId', testPaperId)
    },

    updateVideoUrl (state, data) {
      state.videoUrl = data
      sessionStorage.setItem('videoUrl', JSON.stringify(data))
    },

    updateSectionNodeInfo (state, data) {
      state.sectionNodeInfo = data
      sessionStorage.setItem('sectionNodeInfo', JSON.stringify(data))
    },

    updateMenuIndex (state, data) {
      state.menuIndex = data
      sessionStorage.setItem('menuIndex', data)
    }

  },

  getters: {

    getWebSiteConfig (state) {
      if (state.webSiteConfig != null) {
        return state.webSiteConfig
      }
      return JSON.parse(sessionStorage.getItem('webSiteConfig'))
    },

    getMenuIndex (state) {
      if (state.menuIndex != null) {
        return state.menuIndex
      }
      let data = sessionStorage.getItem('menuIndex')
      if (!data) {
        return "1"
      }
      return data
    },

    getTestPaperInfo (state) {
      if (state.testPaperInfo != null) {
        return state.testPaperInfo
      }
      return JSON.parse(sessionStorage.getItem('testPaperInfo'))
    },

    getTestPaperId (state) {
      if (state.testPaperId != null) {
        return state.testPaperId
      }
      return sessionStorage.getItem('testPaperId')
    },

    getVideoUrl (state) {
      if (state.videoUrl) {
        return state.videoUrl
      }
      return JSON.parse(sessionStorage.getItem('videoUrl'))
    },

    getSectionNodeInfo (state, data) {
      if (state.sectionNodeInfo) {
        return state.sectionNodeInfo
      }
      return JSON.parse(sessionStorage.getItem('sectionNodeInfo'))
    }
  }
}
