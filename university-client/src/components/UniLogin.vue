<template>
  <v-card :loading="loading">

    <v-toolbar dark flat>
      <v-toolbar-title>
        Login form
      </v-toolbar-title>
    </v-toolbar>
  
    <v-card-text>
      <v-form>
        <v-text-field
          v-model="email"
          label="E-mail"
          name="email"
          type="text"
          required
        />

        <v-text-field
          v-model="password"
          id="password"
          label="Password"
          name="password"
          type="password"
          required
        />
      </v-form>
    </v-card-text>

    <v-card-actions>
      <v-btn class="mr-4" @click="login" dark>
        Login
      </v-btn>
      <v-btn class="mr-4" @click="clean" dark>
        Clean
      </v-btn>
    </v-card-actions>

  </v-card>
</template>

<script>
export default {
  name: 'UniLogin',
  data: () => ({
    email: '',
    password: '',
    loading: false,
  }),
  methods: {
    async login() {
      this.loading = true
      try {
        await this.$store.dispatch('auth/login', {
          email: this.email,
          password: this.password
        })
        this.clean()
        this.$emit('success')
      } catch (e) {
        console.error(e.code)
        console.error(e.status)
      } finally {
        this.loading = false
      }
    },
    clean() {
      this.email = ''
      this.password = ''
      this.loading = false
    }
  }
}
</script>
