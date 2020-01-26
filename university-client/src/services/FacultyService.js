import BaseService from 'services/BaseService.js';

class FacultyService extends BaseService {

  async fetchFaculties() {
    try {
      const { data } = await this.api.get('faculties');
      console.debug(data)
      return data;
    } catch (error) {
      console.error(error);
    }
  }

}

export default new FacultyService();
