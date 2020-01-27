import BaseService from 'services/BaseService.js';

class HomeService extends BaseService {

  async fetchLangs() {
    try {
      const { data } = await this.api.get('/langs');

      const res = await this.api.get('/langs/default');
      console.debug(res.data);

      return data.map(lang => `${lang.label} `);
    } catch (error) {
      console.error(error);
    }
  }

  async fetchRoles() {
    try {
      const { data } = await this.api.get('roles');
      return data.map(role => `${role.title} `);
    } catch (error) {
      console.error(error);
     }
  }

}

export default new HomeService();