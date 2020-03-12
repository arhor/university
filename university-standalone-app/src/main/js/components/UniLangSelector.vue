<template>
  <v-menu
    right
    bottom
    offset-y
    transition="slide-y-transition"
  >

    <!-- Menu button icon -->
    <template v-slot:activator="{ on }">
      <v-btn icon v-on="on">
        <v-icon>mdi-translate</v-icon>
      </v-btn>
    </template>

    <!-- Menu languages to select -->
    <v-list>
      <v-list-item
        v-for="(lang, i) in availableLangs"
        :key="`${lang}-${i}`"
        @click="handleSelectedLang(lang)"
      >
        <v-list-item-content>
          <v-list-item-title>{{ lang }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>

  </v-menu>
</template>

<script>
import langs from '@/langs'

export default {
  name: 'UniLangSelector',
  data: () => ({
    availableLangs: Object.keys(langs)
  }),
  methods: {
    handleSelectedLang(lang) {
      this.$store.dispatch('changeLang', lang)
    }
  }
}
</script>