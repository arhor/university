import axios from 'axios'

const state = {
  tokenType: localStorage.getItem('tokenType') || '',
  accessToken: localStorage.getItem('accessToken') || '',
}

const mutations = {
  SET_AUTH: (state, payload) => {
    const { accessToken, tokenType } = payload
    state.tokenType = tokenType
    state.accessToken = accessToken
    localStorage.setItem('tokenType', tokenType)
    localStorage.setItem('accessToken', accessToken)
  },
  INVALIDATE: (state) => {
    state.tokenType = ''
    state.accessToken = ''
    localStorage.removeItem('tokenType')
    localStorage.removeItem('accessToken')
  },
}

const actions = {
  login: async ({ commit }, payload) => {
    try {
      const { email, password } = payload
      const { data } = await axios.post('http://localhost:8080/api/v1/auth/signin', { email, password })
      commit('SET_AUTH', data)
    } catch (error) {
      console.error(error)
    }
  },
  logout: ({ commit }) => {
    commit('INVALIDATE')
  },
  refresh: async ({ getters, commit, dispatch }) => {
    try {
      const { data } = await axios.get('http://localhost:8080/api/v1/auth/refresh', {
        headers: {
          Authorization: getters.authToken
        }
      })
      commit('SET_AUTH', data)
    } catch (error) {
      console.error(error)
      dispatch('logout')
    }
  },
}

const getters = {
  authToken: ({ accessToken, tokenType }) => `${tokenType} ${accessToken}`,
  isAuthenticated: ({ accessToken, tokenType }) => (accessToken !== '' && tokenType !== ''),
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
}
