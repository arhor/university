<template>
  <v-card>
    <v-card-title>
      Login form
    </v-card-title>
    <v-container>
      <v-layout>
        <v-flex>
          <form>
            <v-text-field
              v-model="email"
              :error-messages="emailErrors"
              label="E-mail"
              required
            />
            <v-text-field
              v-model="password"
              label="Password"
              required
            />
            <v-btn class="mr-4" @click="login">
              submit
            </v-btn>
          </form>
        </v-flex>
      </v-layout>
    </v-container>
  </v-card>
  
</template>

<script>
export default {
  name: 'UniLogin',
  data: () => ({
    email: '',
    password: '',
  }),
  methods: {
    login() {
      console.debug('logging in...')
      const { email, password } = this
      this.$store.dispatch('auth/login', { email, password })
    }
  }
}
</script>
