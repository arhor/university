import Service from '../core/Service';

class LocalizationService extends Service {
  async localize(label) {
    let text = localStorage.getItem(label);

    if (text === null || text === undefined) {
      const { data } = this.api.get('labels');
      text = data;
      localStorage.setItem(label, text);
    }

    return text;
  }
}

export default new LocalizationService();
