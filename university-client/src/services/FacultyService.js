import BaseService from 'services/BaseService.js';
import AuthService from 'services/AuthService.js';

class FacultyService extends BaseService {

  async fetchFaculties() {

    const { accessToken, tokenType } = AuthService;

    console.debug(tokenType);
    console.debug(accessToken);

    try {
      const { data } = await this.api.get('faculties', {
        headers: {
          Authorization: `${tokenType} ${accessToken}`
        }
      });
      console.debug(data)
      return data;
    } catch (error) {
      console.error(error);
    }
  }

}

export default new FacultyService();
