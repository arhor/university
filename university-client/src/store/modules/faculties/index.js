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
    } catch (error) {
      console.error(error)
    }
    commit('SET_FACULTIES', [1, 2, 3])
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