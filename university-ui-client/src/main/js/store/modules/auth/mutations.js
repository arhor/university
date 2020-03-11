const SET_AUTH = (state, payload) => {
  const { accessToken, tokenType } = payload
  state.tokenType = tokenType
  state.accessToken = accessToken
  localStorage.setItem('tokenType', tokenType)
  localStorage.setItem('accessToken', accessToken)
}


const INVALIDATE = (state) => {
  state.tokenType = ''
  state.accessToken = ''
  localStorage.removeItem('tokenType')
  localStorage.removeItem('accessToken')
}

export default {
  SET_AUTH,
  INVALIDATE,
}
