/**
 * 全站路由配置
 *
 * 建议:
 * 1. 代码中路由统一使用name属性跳转(不使用path属性)
 */
import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

// 开发环境不使用懒加载, 因为懒加载页面太多的话会造成webpack热更新太慢, 所以只有生产环境使用懒加载
const _import = require('./import-' + process.env.NODE_ENV)

const router = new Router({
  routes: [
    {
      path: '/404',
      component: _import('common/404'),
      name: '404',
      meta: { title: '404未找到' } },
    {
      path: '/login',
      component: _import('common/login'),
      name: 'login',
      meta: { title: '登录' }
    },

    { path: '/',
      component: _import('main'),
      name: 'main',
      redirect: {name: 'home'},
      meta: { title: '主入口整体布局' },
      children: [
        {
          path: '/home',
          component: _import('common/home'),
          name: 'home',
          meta: {
            title: '首页', isTab: false
          }
        },
        // 教育教学管理路由模块
        {
          path: '/subject',
          component: _import('education/subject'),
          name: 'subject',
          meta: { title: '科目管理', isTab: true }
        },
        {
          path: '/question',
          component: _import('education/question'),
          name: 'question',
          meta: { title: '试题管理', isTab: true }
        },

        {
          path: '/questionDetail/:id',
          component: _import('education/questionDetail'),
          name: 'questionDetail',
          meta: { title: '试题详情', isTab: true }
        },

        {
          path: '/questionImport',
          component: _import('education/questionImport'),
          name: 'questionImport',
          meta: { title: '试题导入', isTab: true }
        },

        {
          path: '/course',
          component: _import('education/course'),
          name: 'course',
          meta: { title: '课程管理', isTab: true }
        },

        {
          path: '/courseSection',
          component: _import('education/courseSection'),
          name: 'courseSection',
          meta: { title: '课程章节管理', isTab: true }
        },

        {
          path: '/student',
          component: _import('education/student'),
          name: 'student',
          meta: { title: '学员管理', isTab: true }
        },
        {
          path: '/testPaper',
          component: _import('education/testPaper'),
          name: 'testPaper',
          meta: { title: '试卷管理', isTab: true }
        },

        {
          path: '/advertisement',
          component: _import('system/advertisement'),
          name: 'advertisement',
          meta: { title: '广告管理', isTab: true }
        },

        {
          path: '/autoCreatePaper',
          component: _import('education/autoCreatePaper'),
          name: 'autoCreatePaper',
          meta: { title: '智能组卷', isTab: true }
        },
        {
          path: '/languagePoints',
          component: _import('education/languagePoints'),
          name: 'languagePoints',
          meta: { title: '知识点管理', isTab: true }
        },

        {
          path: '/gradeInfo',
          component: _import('education/gradeInfo'),
          name: 'gradeInfo',
          meta: { title: '年级管理', isTab: true }
        },

        {
          path: '/questionHistory',
          component: _import('education/questionHistory'),
          name: 'questionHistory',
          meta: { title: '答题记录', isTab: true }
        },
        {
          path: '/studentQuestionHistory',
          component: _import('education/studentQuestionHistory'),
          name: 'studentQuestionHistory',
          meta: { title: '试卷试题', isTab: true }
        },

        {
          // path: '/languagePoints/:subjectId/:gradeInfoId',
          path: '/languagePoints',
          component: _import('education/languagePoints'),
          name: 'languagePoints',
          meta: {title: '知识点管理', isTab: true}
        },
        {
          path: '/studentPaperHistory',
          component: _import('education/studentPaperHistory'),
          name: 'studentPaperHistory',
          meta: { title: '学员试卷试题', isTab: true }
        },
        {
          path: '/correctExam',
          component: _import('education/correctExam'),
          name: 'correctExam',
          meta: { title: '学员试卷批改', isTab: true }
        },
        {
          path: '/examReport',
          component: _import('education/examReport'),
          name: 'examReport',
          meta: { title: '考试统计', isTab: true }
        },
        // 系统设置路由模块
        {
          path: '/admin',
          component: _import('system/admin'),
          name: 'admin',
          meta: { title: '管理员列表', isTab: true }
        },
        {
          path: '/role',
          component: _import('system/role'),
          name: 'role',
          meta: { title: '角色列表', isTab: true }
        },
        {
          path: '/onLineUser',
          component: _import('monitor/onLineUser'),
          name: 'onLineUser',
          meta: { title: '在线用户管理', isTab: true }
        },

        {
          path: '/imageSetting',
          component: _import('system/imageSetting'),
          name: 'imageSetting',
          meta: { title: '图片设置', isTab: true }
        },
        {
          path: '/menu',
          component: _import('system/menu'),
          name: 'menu',
          meta: { title: '菜单列表', isTab: true }
        },
        {
          path: '/log',
          component: _import('system/log'),
          name: 'log',
          meta: { title: '日志列表', isTab: true }
        },
        {
          path: '/message',
          component: _import('system/message'),
          name: 'message',
          meta: { title: '系统消息列表', isTab: true }
        },
        {
          path: '/theme',
          component: _import('common/theme'),
          name: 'theme',
          meta: { title: '主题' }
        },
        {
          path: '/dict',
          component: _import('system/dict'),
          name: 'dict',
          meta: { title: '字典管理', isTab: true }
        },

        {
          path: '/examReportDetail',
          component: _import('education/examReportDetail'),
          name: 'examReportDetail',
          meta: { title: '考试分析', isTab: true }
        },

        {
          // path: '/dictValue/:id',
          path: '/dictValue',
          component: _import('system/dictValue'),
          name: 'dictValue',
          // eslint-disable-next-line standard/object-curly-even-spacing
          meta: { title: '字典值管理', isTab: true}
        },

        {
          // path: '/examMonitor/:id',
          path: '/examMonitor',
          component: _import('monitor/exam-monitor'),
          name: 'examMonitor',
          // eslint-disable-next-line standard/object-curly-even-spacing
          meta: { title: '考试监控', isTab: true}
        },

        {
          path: '/webSiteConfig',
          component: _import('system/webSiteConfig'),
          name: 'webSiteConfig',
          // eslint-disable-next-line standard/object-curly-even-spacing
          meta: { title: '网站设置', isTab: true}
        }
      ]
    }
  ]
})

// vue  路由全局拦截器
router.beforeEach(function (to, from, next) {
  let token = localStorage.getItem('Authorization')
  console.log(token)
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
