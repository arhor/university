<template>
  <v-data-table
    :headers="headers"
    :items="allFaculties"
    :items-per-page="10"
    class="elevation-1"
  ></v-data-table>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'UniFacultiesList',
  data() {
    return {
      headers: [
        { text: this.$options.filters.translate('title'), value: 'defaultTitle' },
        { text: this.$options.filters.translate('seatsPaid'), value: 'seatsPaid' },
        { text: this.$options.filters.translate('seatsBudget'), value: 'seatsBudget' },
      ]
    }
  },
  computed: {
    ...mapGetters('faculties', ['allFaculties']),
    ...mapGetters('auth', ['role'])
  },
  beforeCreate() {
    this.$store.dispatch('faculties/load')
  }
}
</script>
