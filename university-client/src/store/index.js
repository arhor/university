import Vue from 'vue'
import Vuex from 'vuex'
import modules from '@/store/modules'

Vue.use(Vuex)

const state = {
  lang: 'RU'
}

const mutations = {
  SET_LANG(state, selectedLang) {
    state.lang = selectedLang
  }
}

const actions = {
  changeLang({ commit }, payload) {
    console.log(payload)
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
