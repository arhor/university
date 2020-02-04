<template>
  <v-app>

    <!-- drawer -->
    <v-navigation-drawer
      app
      v-model="drawer"
      absolute
      temporary
      />

    <!-- navigation -->
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
      <v-btn v-else text @click="dialog = !dialog">
        Sign in
      </v-btn>
    </v-app-bar>

    <!-- main view -->
    <v-content>
      <v-container class="fill-height" fluid>
        <router-view/>
      </v-container>
    </v-content>
    
    <!-- footer -->
    <v-footer color="primary" dark fixed app>
      <v-spacer/>
      <span class="px-3">Maksim Buryshynets &copy; {{ new Date().getFullYear() }}</span>
    </v-footer>

    <!-- login modal -->
    <v-dialog v-model="dialog" max-width="800">
      <uni-login/>
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
  },
  beforeCreate() {
    if (this.$store.getters['auth/isAuthenticated']) {
      this.$store.dispatch('auth/refresh')
    }
  }
}
</script>
