<template>
  <v-app>
    <v-navigation-drawer app v-model="drawer" absolute temporary></v-navigation-drawer>
    <v-app-bar
      app
      color="primary"
      dark
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"/>
      <v-toolbar-title>
        <v-btn text to="/">
          University app
        </v-btn>
      </v-toolbar-title>
      <v-spacer/>
      <v-btn v-if="isAuthenticated" text @click="logout">
        Logout
      </v-btn>
      <v-btn v-else text to="/login">
        Sign in
      </v-btn>
    </v-app-bar>
    <v-content>
      <v-container class="fill-height" fluid>
        <router-view/>
      </v-container>
    </v-content>
    <v-footer color="primary" dark fixed app>
      <v-spacer/>
      <span class="px-3">
        Maksim Buryshynets &copy; {{ new Date().getFullYear() }}
      </span>
    </v-footer>
  </v-app>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'App',

  components: {
  },

  data: () => ({
    drawer: false,

  }),

  computed: {
    ...mapGetters('auth', ['isAuthenticated']),
  },

  methods: {
    ...mapActions('auth', ['logout']),
  },

  beforeCreate() {
    this.$store.dispatch('faculties/load')
  }
}
</script>
