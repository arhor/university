import axios from 'axios'

const state = {
  authenticated: false,
  accessToken: null,
  tokenType: null,
}

const mutations = {
  SET_AUTH: (state, { accessToken, tokenType }) => {
    state.authenticated = true
    state.accessToken = accessToken
    state.tokenType = tokenType
  },
  INVALIDATE: (state) => {
    state.authenticated = false
    state.accessToken = null
    state.tokenType = null
  },
  PERSIST: (state) => {
    localStorage.setItem('authenticated', state.authenticated);
    localStorage.setItem('accessToken', state.accessToken);
    localStorage.setItem('tokenType', state.tokenType);
  },
  RESTORE
}

const actions = {
  init: (store) => {
    const isAuthenticated = !!localStorage.getItem('authenticated')
    if (isAuthenticated) {
      store.commit('SET_AUTH', {
        accessToken: localStorage.getItem('accessToken'),
        tokenType: localStorage.getItem('tokenType')
      })
    }
  },

  login: async (store, { email, password }) => {
    try {
      const { data } = await axios.post('http://localhost:8080/api/v1/', { email, password })
      store.commit('SET_AUTH', { data })
    } catch (error) {
      console.error(error)
    }
  },

  logout: (store) => {
    store.commit('INVALIDATE')
  }
}

const getters = {
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
}
