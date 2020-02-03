import axios from 'axios'

const state = {
  all: []
}

const mutations = {
  SET_FACULTIES(state, items) {
    state.all = items
  }
}

const actions = {
  load: async ({ commit }) => {
    try {
      const { data } = await axios.get('http://localhost:8080/api/v1/faculties')
      console.debug(data)
      commit('SET_FACULTIES', data)
    } catch (error) {
      console.error(error)
    }
  }
}

const getters = {}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}