// initial state
const state = {
  user: null, //用户信息，包括email,nickname等

  dataLoading: false, //加载数据
  headerActive: '' //顶部菜单当前激活选项,
}

// getters
const getters = {
  user: state => state.user,

  dataLoading: state => state.dataLoading,
  headerActive: state => state.headerActive
}

// actions
const actions = {
  setUser({ commit, state }, user) {
    commit('setAccountUser', user)
  },

  setDataLoading({ commit, state }, value) {
    commit('setdataLoadingState', value)
  },
  setHeaderActive({ commit, state }, value) {
    commit('setHeaderActiveState', value)
  }
}

// mutations
const mutations = {
  setAccountUser(state, user) {
    state.user = user
  },

  setdataLoadingState(state, value) {
    state.dataLoading = value
  },
  setHeaderActiveState(state, value) {
    state.headerActive = value
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
