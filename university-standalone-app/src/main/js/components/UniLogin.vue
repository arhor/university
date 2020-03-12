<template>
  <v-card :loading="loading">

    <v-toolbar dark flat>
      <v-toolbar-title>
        Login form
      </v-toolbar-title>
    </v-toolbar>
  
    <v-card-text>
      <v-form
        ref="form"
        v-model="valid"
        lazy-validation
      >
        <v-text-field
          v-model="email"
          label="E-mail"
          name="email"
          type="text"
          :rules="[v => !!v || 'E-mail is requred']"
          required
        />

        <v-text-field
          v-model="password"
          label="Password"
          name="password"
          type="password"
          :rules="[v => !!v || 'Password is required']"
          required
        />

        <div v-if="error">{{ error }}</div>
      </v-form>
    </v-card-text>

    <v-card-actions>
      <v-btn class="mr-4" @click="login" color="success" :disabled="!valid">
        Login
      </v-btn>
      <v-btn class="mr-4" @click="clean" color="error">
        Clean
      </v-btn>
    </v-card-actions>

  </v-card>
</template>

<script>
export default {
  name: 'UniLogin',
  data: () => ({
    valid: true,
    email: '',
    password: '',
    loading: false,
    error: ''
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
        this.error = e
      } finally {
        this.loading = false
      }
    },
    clean() {
      this.valid = true
      this.email = ''
      this.password = ''
      this.loading = false
      this.error = ''
    }
  }
}
</script>
