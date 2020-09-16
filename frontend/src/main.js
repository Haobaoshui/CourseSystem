import Vue from 'vue'
import App from './App.vue'
import './plugins/element.js'

Vue.config.productionTip = false

import store from './store'
import router from './router'
import axios from './httpconfig/http'
Vue.prototype.$http = axios

import util from './utils/util'
Vue.use(util)

import account from './utils/account'
Vue.use(account)

//学院、系部、班级组织
import school from './utils/school'
import department from './utils/department'
import naturalclass from './utils/naturalclass'
Vue.use(school)
Vue.use(department)
Vue.use(naturalclass)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')