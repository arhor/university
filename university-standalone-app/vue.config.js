const path = require('path')

const sourceDir = 'src/main/js'

module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  configureWebpack: {
    entry: `./${sourceDir}/main.js`,
    resolve: {
      alias: {
        '@': path.resolve(__dirname, sourceDir),
      },
    },
  },
}