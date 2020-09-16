import Vue from 'vue'
import Router from 'vue-router'

import store from '@/store/index'

const Login = () => import('@/views/user/Login.vue')
const Register = () => import('@/views/user/Register.vue')

/**
 * 错误页面
 */

const ServerError = () => import('@/views/errors/ServerError.vue')
const NotFound = () => import('@/views/errors/NotFound.vue')

const Welcome = () => import('@/views/Welcome.vue')

/**
 * 课程页面
 */
const Main = () => import('@/views/courses/Main.vue')
const CoursesList = () => import('@/views/courses/CoursesList.vue')

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [{
      path: '/',
      name: 'welcome',
      component: Welcome,
      beforeEnter: (to, from, next) => {
        store.dispatch('setHeaderActive', '/')
        next()
      }
    },

    {
      path: '/login',
      name: 'Login',
      component: Login,
      beforeEnter: (to, from, next) => {
        store.dispatch('setHeaderActive', '/')
        next()
      }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register,
      beforeEnter: (to, from, next) => {
        store.dispatch('setHeaderActive', '/')
        next()
      }
    },
    {
      path: '/servererror',
      name: 'servererror',
      component: ServerError,
      beforeEnter: (to, from, next) => {
        store.dispatch('setHeaderActive', '/')
        next()
      }
    },
    {
      path: '/help',
      component: () => import('@/views/Help')
    },
    {
      path: '/courses/',
      component: Main,
      children: [{
        // 当 /user/:id/profile 匹配成功，
        // UserProfile 会被渲染在 User 的 <router-view> 中
        path: '',
        component: CoursesList
      }]
    },

    {
      path: '/home',
      component: Main,

      children: [


        {
          path: 'user',
          component: () => import('@/views/user/UserContainer'),

          children: [

            {
              path: 'profile',
              component: () => import('@/views/user/Profile')
            },

            {
              path: 'changepwd',
              component: () => import('@/views/user/ChangePwd')
            },

            {
              path: 'logs',
              component: () => import('@/views/user/UserLogs')
            },

            {
              path: '',
              component: () => import('@/views/user/Profile')
            },

          ]
        },

        {
          path: 'organization',
          component: () => import('@/views/organization/OrganizationContainer'),

          children: [

            {
              path: 'school',
              component: () => import('@/views/organization/SchoolManager')
            },
            {
              path: 'department',
              component: () => import('@/views/organization/DepartmentManager')
            },






          ]
        },

        {
          path: '',

          component: () => import('@/views/Welcome')
        }
      ]
    },

    {
      path: '*',
      component: NotFound
    }
  ],
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return {
        x: 0,
        y: 0
      }
    }
  }
})

export default router