import dictionary from '@/langs'
import store from '@/store'

export default function translate(label) {
  return dictionary[store.state.lang][label]
}