<template>
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
</template>

<script>
export default {
  name: 'login',
  data: () => ({
    email: '',
    password: '',
  }),
  methods: {
    login() {
      console.debug('logging in...')
      this.$store.dispatch('auth/login', {
        email: this.email,
        password: this.password
      })
    }
  }
}
</script>
