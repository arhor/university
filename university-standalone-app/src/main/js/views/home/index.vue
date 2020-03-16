<template>
  <v-row>
    <v-col>
      <v-row justify="center" align="center">
        <v-col cols="6" justify="center" align="center">
          <v-img
            :src="require('@/assets/img/university-logo.jpg')"
            aspect-ratio="1"
            class="grey lighten-2"
            max-width="500"
            max-height="300"
          ></v-img>
        </v-col>
      </v-row>
      <v-row justify="center" align="center">
        <v-col cols="6">
          <p class="text-center">Добро пожаловать в университет ЯНПН!</p>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: 'Home'
}
</script>
