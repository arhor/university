<template>
  <v-card :loading="loading">

    <v-toolbar dark flat>
      <v-toolbar-title>
        Enroll form
      </v-toolbar-title>
    </v-toolbar>

    <v-card-text>
      <v-form
        ref="form"
        v-model="valid"
        lazy-validation
      >
        <v-text-field
          v-model="country"
          label="Country"
          name="country"
          type="text"
          :rules="[v => !!v || 'Country is required']"
          required
        />

        <v-text-field
          v-model="city"
          label="City"
          name="city"
          type="text"
          :rules="[v => !!v || 'City is required']"
          required
        />

        <v-text-field
          v-model="schoolScore"
          label="School Score"
          name="schoolScore"
          type="number"
          :rules="[v => !!v || 'School Score is required']"
          required
        />

        <div v-if="error">{{ error }}</div>
      </v-form>
    </v-card-text>

    <v-card-actions>
      <v-btn class="mr-4" @click="enroll" color="success" :disabled="!valid">
        Enroll
      </v-btn>
      <v-btn class="mr-4" @click="clean" color="error">
        Clean
      </v-btn>
    </v-card-actions>

  </v-card>
</template>

<script>
export default {
  name: 'UniEnroll',
  data: () => ({
    valid: true,
    country: '',
    city: '',
    schoolScore: 0,
    loading: false,
    error: ''
  }),
  methods: {
    async enroll() {
      this.loading = true
      try {
        await this.$store.dispatch('enrollees/enroll', {
          country: this.country,
          city: this.city,
          schoolScore: this.schoolScore,
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
      this.country = ''
      this.city = ''
      this.schoolScore = ''
      this.loading = false
      this.error = ''
    }
  }
}
</script>
