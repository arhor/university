package by.arhor.core;

public interface Visitable<V> {

  void accept(Visitor<Visitable<V>> visitor);

}
