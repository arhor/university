package by.arhor.university.core.pattern.visitor;

public interface Visitable<V> {

  void accept(Visitor<Visitable<V>> visitor);

}
