import axios from 'axios'
import uuid from './uuid.js'

const XSRF_TOKEN = uuid()
const BASE_URL = 'http://localhost:8080/api/v1/'

export default class Service {

  get xsrfToken() {
    return XSRF_TOKEN
  }

  get baseUrl() {
    return BASE_URL
  }

}
