import Service from '../../../core/Service.js';

class HomeService extends Service {

  async fetchLangs() {
    try {
      const { data } = await this.api.get('langs');
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