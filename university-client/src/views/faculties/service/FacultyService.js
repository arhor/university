import Service from '../../../core/Service.js';

class FacultyService extends Service {

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
