import Vue from 'vue'
import Router from 'vue-router'
import index from '@/views/index'
import home from '@/views/home'
import login from '@/views/login'
import video from '@/views/course/video'
import wrongBook from '@/views/education/wrongBook'
import writingBoard from '@/views/writingBoard'
import paper from '@/views/education/paper'
import examQuestion from '@/views/education/examQuestion'

import about from '@/views/about'
import examHistory from '@/views/education/examHistory'
import courseDetail from '@/views/course/courseDetail'
import coursePlay from '@/views/course/coursePlayDetail'
import courseVideo from '@/views/course/courseVideo'

import examReport from '@/views/education/examReport'
import userCenter from "../views/userCenter/index"
import userInfo from "../views/userCenter/userInfo"
import password from "../views/userCenter/password"
import message from "../views/userCenter/message"

import myCourseCenter from "../views/myCourseCenter/index"
import myCourse from "../views/myCourseCenter/myCourse"
Vue.use(Router)

const router =  new Router({
  routes: [
    {
      path: '/login',
      component: login,
      name: 'login',
    },

    {
      path: '/video',
      component: video,
      name: 'video',
      meta: { title: '视频播放' }
    },

    {
      path: '/examQuestion',
      component: examQuestion,
      name: 'examQuestion',
      meta: { title: '试卷试题页面' }
    },

    {
      path: '/writingBoard',
      component: writingBoard,
      name: 'writingBoard',
    },
    { path: '/',
      component: index,
      name: 'index',
      redirect: {name: 'home'},
      meta: { title: '主入口整体布局' },
      children: [
        {
          path: '/home',
          component: home,
          name: 'home',
          meta: { title: '首页' }
         },

        {
          path: '/courseVideo',
          component: courseVideo,
          name: 'courseVideo',
          meta: { title: '视频课堂' }
        },

        {
          path: '/coursePlay',
          component: coursePlay,
          name: 'coursePlay',
          meta: { title: '课程视频播放' }
        },

        {
          path: '/account',
          component: userCenter,
          name: 'userCenter',
          meta: { title: '个人中心' },
          children: [
            {
              path: '/account/userInfo',
              component: userInfo,
              name: 'userInfo',
              meta: { title: '个人资料' }
            },

            {
              path: '/account/password',
              component: password,
              name: 'password',
              meta: { title: '密码修改' }
            },

            {
              path: '/account/message',
              component: message,
              name: 'message',
              meta: { title: '消息管理' }
            }
          ]
        },


        {
          path: '/courseHistory',
          component: myCourseCenter,
          name: 'myCourseCenter',
          meta: { title: '个人中心' },
          children: [
            {
              path: '/courseHistory/myCourse',
              component: myCourse,
              name: 'myCourse',
              meta: { title: '我的课程' }
            }
          ]
        },

        {
          path: '/wrongBook',
          component: wrongBook,
          name: 'wrongBook',
          meta: { title: '错题本' }
        },

        {
          path: '/paper',
          component: paper,
          name: 'paper',
          meta: { title: '考试列表' }
         },

        {
          path: '/examReport/:id',
          component: examReport,
          name: 'examReport',
          meta: { title: '考试报告' }
         },

        {
          path: '/about',
          component: about,
          name: 'about',
          meta: { title: '关于我们' }
         },

        {
          path: '/examHistory',
          component: examHistory,
          name: 'examHistory',
          meta: { title: '空白页面' }
         },


        {
          path: '/courseDetail/:id',
          component: courseDetail,
          name: 'courseDetail',
          meta: { title: '空白页面' }
         }
      ]
    }
  ]
})

// vue  路由全局拦截器
router.beforeEach(function (to, from, next) {
  let token = localStorage.getItem('token')
  if (to.path === '/login') {
    if (token) {
      location.href = '/'
    } else {
      next()
    }
  } else {
    if (!token) {
      next({ name: 'login' }) // 没有token 跳转登录页面
    }
    next()
  }
})
export default router
