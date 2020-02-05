import Vue from 'vue'
import App from '@/App.vue'
import router from '@/router'
import store from '@/store'
import vuetify from '@/plugins/vuetify'
import filters from '@/filters'

for (let name in filters) {
  if (filters.hasOwnProperty(name)) {
    Vue.filter(name, filters[name])
  }
}

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
