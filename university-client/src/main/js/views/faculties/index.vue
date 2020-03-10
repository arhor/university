<template>
  <v-layout row>
    <v-flex col="6">
      <uni-faculties-list />
    </v-flex>
  </v-layout>
</template>

<script>
import UniFacultiesList from '@/views/faculties/components/UniFacultiesList.vue'

export default {
  name: 'Faculties',
  components: {
    UniFacultiesList
  }
}
</script>
