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
  data: () => ({
    headers: [
      { text: 'Id', value: 'id' },
      { text: 'Title', value: 'defaultTitle' },
      { text: 'Seats paid', value: 'seatsPaid' },
      { text: 'Seats budget', value: 'seatsBudget' },
    ]
  }),
  computed: {
    ...mapGetters('faculties', ['allFaculties'])
  },
  beforeCreate() {
    this.$store.dispatch('faculties/load')
  }
}
</script>
