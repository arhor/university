import axios from 'axios';
import Service from '../../../core/Service.js';

export default class HomeService extends Service {

  async fetchLangs() {
    try {
      const { data } = await axios.get(this.baseUrl + 'langs');
      return data.map(lang => `${lang.label} `);
    } catch (error) {
      console.error(error);
    }
  }

  async fetchRoles() {
    try {
      const { data } = await axios.get(this.baseUrl + 'roles');
      return data.map(role => `${role.title} `);
    } catch (error) {
      console.error(error);
     }
  }

}