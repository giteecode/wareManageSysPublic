export default {

    namespaced: true,
    state: {
        courseInfo: null,
		courseSectionNode: null
    },

    getters: {
        getCourseInfo: state => {
            if (state.courseInfo) {
                return state.courseInfo
            }
            let courseInfo = uni.getStorageSync('courseInfo')
            if (courseInfo) {
                return JSON.parse(courseInfo)
            }
            return null
        },
		
		getCourseSectionNodeInfo: state => {
		    if (state.courseSectionNode) {
		        return state.courseSectionNode
		    }
		    let courseSectionNode = uni.getStorageSync('courseSectionNode')
		    if (courseSectionNode) {
		        return JSON.parse(courseSectionNode)
		    }
		    return null
		}
    },

    mutations: {
        updateCourseInfo (state, courseInfo) {
            state.courseInfo = courseInfo
            uni.setStorage({
                key: 'courseInfo',
                data: JSON.stringify(courseInfo)
            })
        },
		
		updateCourseSectionNodeInfo (state, courseSectionNode) {
		    state.courseSectionNode = courseSectionNode
		    uni.setStorage({
		        key: 'courseSectionNode',
		        data: JSON.stringify(courseSectionNode)
		    })
		}
    }
}