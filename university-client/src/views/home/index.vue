<template>
  <v-layout row>
    <v-flex>
      {{ 'welcome' | translate }} to the University app!
      <br/>
      <v-select
        :items="['RU', 'BY', 'EN']"
        @change="changeLang"
        label="Standard"
      />
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  name: 'home',
  components: {
  },
  methods: {
    changeLang(e) {
      this.$store.dispatch('changeLang', e)
    }
  }
}
</script>
