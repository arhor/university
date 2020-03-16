<template>
  <v-row>
    <v-col cols="6">
      <uni-faculties-list />
    </v-col>
  </v-row>
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
