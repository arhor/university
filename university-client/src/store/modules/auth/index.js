import axios from 'axios'

const state = {
  accessToken: localStorage.getItem('accessToken') || '',
  tokenType: localStorage.getItem('tokenType') || '',
}

const mutations = {
  SET_AUTH: (state, { accessToken, tokenType }) => {
    state.accessToken = accessToken
    state.tokenType = tokenType
    localStorage.setItem('accessToken', accessToken)
    localStorage.setItem('tokenType', tokenType)
  },
  INVALIDATE: (state) => {
    state.accessToken = ''
    state.tokenType = ''
    localStorage.removeItem('accessToken')
    localStorage.removeItem('tokenType')
  },
}

const actions = {
  login: async (store, payload) => {
    try {
      const { email, password } = payload
      console.log(`try to login with ${email} and ${password}`)
      const { data } = await axios.post('http://localhost:8080/api/v1/auth/signin', { email, password })
      console.log(data)
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
  authToken: state => `${state.tokenType} ${state.accessToken}`,
  isAuthenticated: (state) => {
    const { accessToken, tokenType } = state
    return accessToken !== ''
        && tokenType !== ''
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
}
