import axios from 'axios'

const enroll = async ({ commit, rootGetters }, { country, city, schoolScore }) => {
  try {
    const isAuthenticated = rootGetters['auth/isAuthenticated']

    const { data } =
        isAuthenticated
            ? await axios.post('http://localhost:8080/api/v1/enrollees', { country, city, schoolScore }, {
                headers: { Authorization: rootGetters['auth/authToken'] }
              })
            : await axios.post('http://localhost:8080/api/v1/enrollees', { country, city, schoolScore })

    console.debug(data)
    commit('SET_ENROLLEE', data)
  } catch (error) {
    console.error(error)
    throw error
  }
}

const unroll = ({ commit }) => commit('CLEAR_ENROLLEE')

export default {
  enroll,
  unroll,
}
