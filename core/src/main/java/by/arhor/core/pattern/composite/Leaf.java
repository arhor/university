package by.arhor.core.pattern.composite;

import javax.annotation.Nullable;

public interface Leaf<T> extends Composite<T> {

  @Nullable
  T getValue();

  void setValue(@Nullable T value);

  static <T> Leaf<T> leaf(T value) {
    return CompositeFactory.buildLeaf(value);
  }
}
