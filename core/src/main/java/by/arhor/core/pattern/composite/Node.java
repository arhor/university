package by.arhor.core.pattern.composite;

import javax.annotation.Nonnull;

public interface Node<T> extends Composite<T> {

  void add(@Nonnull Composite<T> child);

  @SafeVarargs
  static <T> Node<T> node(@Nonnull Composite<T>... children) {
    return CompositeFactory.buildNode(children);
  }
}
