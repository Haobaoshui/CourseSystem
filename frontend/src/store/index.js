import Vue from 'vue'
import Vuex from 'vuex'
import storeAppData from './modules/storeAppData'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    storeAppData
  }
})
