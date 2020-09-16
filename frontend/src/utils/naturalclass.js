import store from './../store/index'

export default {
  install(Vue, options) {
    /**
     * 设置用户
     */
    Vue.prototype.setUser = function(userobj) {
      store.dispatch('setUser', {
        userAccount: userobj.userAccount,
        userEmail: userobj.userEmail,
        userNickname: userobj.userNickname,
        userPhotoId: userobj.userPhotoId,
        userPhotoImg: userobj.userPhotoImg
      })
    }

    /**
     * 获得用户名字
     */
    Vue.prototype.getUsername = function() {
      if (this.isNull(store.state)) return ''
      if (this.isNull(store.state.storeAppData)) return ''
      if (this.isNull(store.state.storeAppData.user)) return ''
      if (this.isNull(store.state.storeAppData.user.userNickname)) return ''
      return store.state.storeAppData.user.userNickname
    }

    /**
     * 获得用户id，此id等同于ETH账号
     */
    Vue.prototype.getUserId = function() {
      if (this.isNull(store.state)) return ''
      if (this.isNull(store.state.storeAppData)) return ''
      if (this.isNull(store.state.storeAppData.user)) return ''
      if (this.isNull(store.state.storeAppData.user.userAccount)) return ''
      return store.state.storeAppData.user.userAccount
    }

    /**
     * 获得用户email
     */
    Vue.prototype.getUserEmail = function() {
      if (this.isNull(store.state)) return ''
      if (this.isNull(store.state.storeAppData)) return ''
      if (this.isNull(store.state.storeAppData.user)) return ''
      if (this.isNull(store.state.storeAppData.user.userEmail)) return ''
      return store.state.storeAppData.user.userEmail
    }

    /**
     * 获得用户头像
     */
    Vue.prototype.getUserAvatarImg = function() {
      if (this.isNull(store.state)) return ''
      if (this.isNull(store.state.storeAppData)) return ''
      if (this.isNull(store.state.storeAppData.user)) return ''
      if (this.isNull(store.state.storeAppData.user.userPhotoImg)) return ''
      return store.state.storeAppData.user.userPhotoImg
    }

    /**
     * 获得用户头像id
     */
    Vue.prototype.getUserAvatarId = function() {
      if (this.isNull(store.state)) return ''
      if (this.isNull(store.state.storeAppData)) return ''
      if (this.isNull(store.state.storeAppData.user)) return ''
      if (this.isNull(store.state.storeAppData.user.userPhotoId)) return ''
      return store.state.storeAppData.user.userPhotoId
    }

    /**
     * 用户是否登录
     */
    Vue.prototype.isSignin = function() {
      if (this.isNull(this.getUserId())) return false
      return true
    }
  }
}
