<template>
  <v-app>

    <!-- drawer -->
    <v-navigation-drawer
      app
      absolute
      overflow
      temporary
      v-model="drawer"
    ></v-navigation-drawer>

    <!-- navigation -->
    <v-app-bar
      app
      dark
      fixed
      dense
      :src="require('@/assets/university-app-bar-bg.jpg')"
    >
      <template v-slot:img="{ props }">
        <v-img
          v-bind="props"
          gradient="to top right, rgba(100,115,201,.7), rgba(25,32,72,.7)"
        ></v-img>
      </template>

      <v-app-bar-nav-icon @click.stop="drawer = !drawer"/>

      <v-toolbar-title>University app</v-toolbar-title>

      <v-spacer />

      <v-btn v-if="isAuthenticated" text @click="logout">
        Logout
      </v-btn>

      <v-btn v-else text @click="toggleLoginDialog">
        Sign in
      </v-btn>

      <template v-slot:extension>
        <v-tabs align-with-title>
          <v-tab to="/">Home</v-tab>
          <v-tab to="/faculties">Faculties</v-tab>
        </v-tabs>
      </template>
    </v-app-bar>

    <!-- main view -->
    <v-content>
      <v-container class="fill-height" fluid>
        <router-view />
      </v-container>
    </v-content>
    
    <!-- footer -->
    <v-footer app dark fixed>
      <v-spacer/>
      <span class="px-3">Maksim Buryshynets &copy; {{ new Date().getFullYear() }}</span>
    </v-footer>

    <!-- login modal -->
    <v-dialog v-model="dialog" max-width="800">
      <uni-login @success="toggleLoginDialog" />
    </v-dialog>

  </v-app>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import UniLogin from '@/components/UniLogin.vue'

export default {
  name: 'App',
  components: {
    UniLogin
  },
  data: () => ({
    drawer: false,
    dialog: false,
  }),
  computed: {
    ...mapGetters('auth', ['isAuthenticated']),
  },
  methods: {
    ...mapActions('auth', ['logout']),
    toggleLoginDialog() {
      this.dialog = !this.dialog
    },
  },
  beforeCreate() {
    if (this.$store.getters['auth/isAuthenticated']) {
      this.$store.dispatch('auth/refresh')
    }
  }
}
</script>

<style>
body, html {
  overflow:hidden;
}
</style>
