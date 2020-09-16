import store from './../store/index'

export default {
  install(Vue, options) {
    /**
     * tmp是否为空
     * @param {*} tmp
     */
    Vue.prototype.isNull = function(tmp) {
      if (!tmp || typeof tmp == 'undefined' || tmp == 0 || tmp == null) {
        return true
      }
      return false
    }

    /**
     * 是否装载数据
     */
    Vue.prototype.getDataLoading = function() {
      if (this.isNull(store.state)) return false

      if (this.isNull(store.state.storeAppData)) return false

      return store.state.storeAppData.dataLoading.value
    }

    Vue.prototype.startDataLoading = function() {
      store.dispatch('setDataLoading', { value: true })
    }

    Vue.prototype.stopDataLoading = function() {
      store.dispatch('setDataLoading', { value: false })
    }

    /**
     * 当前路由跳转到到‘servererror’
     */
    Vue.prototype.gotoServerError = function() {
      this.$router.push({
        name: 'servererror'
      })
    }
  }
}
