import BASEURL from "@/httpconfig/api.js";

export default {
  install(Vue, options) {
    /**
     * 设置用户
     */
    Vue.prototype.setUser = function (userobj) {
      store.dispatch('setUser', {
        userAccount: userobj.userAccount,
        userEmail: userobj.userEmail,
        userNickname: userobj.userNickname,
        userPhotoId: userobj.userPhotoId,
        userPhotoImg: userobj.userPhotoImg
      })
    }

  }
}