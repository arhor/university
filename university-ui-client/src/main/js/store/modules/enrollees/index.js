import actions from '@/store/modules/enrollees/actions.js'
import getters from '@/store/modules/enrollees/getters.js'
import mutations from '@/store/modules/enrollees/mutations.js'

const state = {
  country:  '',
  city: '',
  schoolScore: 0,
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
}
