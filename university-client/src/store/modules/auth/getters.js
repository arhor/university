import getJwtPayload from '@/util/jwt.js'

const authToken = ({ accessToken, tokenType }) => {
  return `${tokenType} ${accessToken}`
}

const isAuthenticated = ({ accessToken, tokenType }) => {
  return accessToken !== ''
      && tokenType !== ''
}

const role = ({ accessToken }) => getJwtPayload(accessToken)

export default {
  authToken,
  isAuthenticated,
  role,
}
