import Service from './Service.js';

class AuthService extends Service {

  get auth_token() {
    return window.localStorage.getItem('auth_token');
  }

  set auth_token(token) {
    window.lo
  }

}

export default new AuthService();
