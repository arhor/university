const SET_ENROLLEE = (state, { country, city, schoolScore }) => {
  state.country = country
  state.city = city
  state.schoolScore = schoolScore
}

const CLEAR_ENROLLEE = (state) => {
  state.country = ''
  state.city = ''
  state.schoolScore = 0
}

export default {
  SET_ENROLLEE,
  CLEAR_ENROLLEE,
}
