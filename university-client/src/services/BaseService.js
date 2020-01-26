import axios from 'axios';
//import uuid from './uuid.js';

//const XSRF_TOKEN = uuid();

//axios.defaults.xsrfCookieName = 'xsrf_token';
//axios.defaults.xsrfHeaderName = 'x-xsrf-token';
//axios.defaults.withCredentials = true;
//axios.defaults.headers.common['x-xsrf-token'] = XSRF_TOKEN;
//axios.defaults.headers.common['Cookie'] = `xsrf_token=${XSRF_TOKEN};`;


axios.defaults.baseURL = 'http://localhost:8080/api/v1/';

export default class BaseService {
  api = axios;
}
