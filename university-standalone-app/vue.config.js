const path = require('path')

const sourceDir = 'src/main/js'
const outputDir = 'build/resources/main/static/'

module.exports = {
  outputDir: path.resolve(__dirname, outputDir),
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