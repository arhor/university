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
  login: async (store, payload) => {
    try {
      const { email, password } = payload
      const { data } = await axios.post('http://localhost:8080/api/v1/auth/signin', { email, password })
      store.commit('SET_AUTH', data)
    } catch (error) {
      console.error(error)
    }
  },
  logout: (store) => {
    store.commit('INVALIDATE')
  },
  refresh: async (store) => {
    const { authToken } = store.getters
    try {
      const { data } = await axios.get('http://localhost:8080/api/v1/auth/refresh', {
        headers: {
          Authorization: authToken
        }
      })
      store.commit('SET_AUTH', data)
    } catch (error) {
      console.error(error)
      store.dispatch('logout')
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
