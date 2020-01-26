import BaseService from 'services/BaseService.js';

class AuthService extends BaseService {

  async signIn({email, password}) {
    try {
      const { data } = await this.api.post('/auth/signin', { email, password });
      console.log(data);
      return true;
    } catch (e) {
      console.error('authentication failed: ', e);
    }
    return false;
  }

  async refresh() {
    const { data } = await this.api.post('/auth/refresh', {})
  }

  invalidate() {
    token_type = undefined;
    auth_token = undefined;
    refresh_token = undefined;
  }

  get token_type() {
    return window.localStorage.getItem('token_type');
  }

  set token_type(type) {
    window.localStorage.setItem('token_type', type);
  }

  get auth_token() {
    return window.localStorage.getItem('auth_token');
  }

  set auth_token(token) {
    window.localStorage.setItem('auth_token', token);
  }

  get refresh_token() {
    return window.localStorage.getItem('refresh_token');
  }

  set refresh_token(token) {
    window.localStorage.setItem('refresh_token', token);
  }

}

export default new AuthService();
