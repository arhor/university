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

  async signUp({email, password, firstName, lastName}) {
      try {
        const { data } = await this.api.post('/auth/signup', { email, password, firstName, lastName });
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
    this.tokenType = undefined;
    this.accessToken = undefined;
    this.refreshToken = undefined;
  }

  get tokenType() {
    return window.localStorage.getItem('tokenType');
  }

  set tokenType(type) {
    window.localStorage.setItem('tokenType', type);
  }

  get accessToken() {
    return window.localStorage.getItem('accessToken');
  }

  set accessToken(token) {
    window.localStorage.setItem('accessToken', token);
  }

  get refreshToken() {
    return window.localStorage.getItem('refreshToken');
  }

  set refreshToken(token) {
    window.localStorage.setItem('refreshToken', token);
  }

}

export default new AuthService();
