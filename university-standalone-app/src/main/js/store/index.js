import Vue from 'vue'
import Vuex from 'vuex'
import modules from '@/store/modules'

Vue.use(Vuex)

const state = {
  currentLang: 'RU'
}

const mutations = {
  SET_LANG(state, selectedLang) {
    state.currentLang = selectedLang
  }
}

const actions = {
  changeLang({ commit }, payload) {
    commit('SET_LANG', payload)
  }
}

const getters = {
}

export default new Vuex.Store({
  state,
  mutations,
  actions,
  getters,
  modules,
})
