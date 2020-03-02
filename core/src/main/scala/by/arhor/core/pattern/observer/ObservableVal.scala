package by.arhor.core.pattern.observer

trait ObservableVal[@specialized(Int) T <: AnyVal] extends Observable[T] {

  def set(value: T): Unit

  def get: T

}

