import actions from '@/store/modules/auth/actions.js'
import getters from '@/store/modules/auth/getters.js'
import mutations from '@/store/modules/auth/mutations.js'

const state = {
  tokenType: localStorage.getItem('tokenType') || '',
  accessToken: localStorage.getItem('accessToken') || '',
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
}
