package by.arhor.core.pattern.composite;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

final class CompositeFactory {

  @SafeVarargs
  static <T> Node<T> buildNode(Composite<T>... children) {
    return new NodeImpl<>(children);
  }

  static <T> Leaf<T> buildLeaf(T value) {
    return new LeafImpl<>(value);
  }

  private static final class NodeImpl<T> implements Node<T> {
    private final List<Composite<T>> children;

    @SafeVarargs
    NodeImpl(Composite<T>... children) {
      this.children = Arrays.stream(children)
          .filter(Objects::nonNull)
          .collect(toList());
    }

    @Override
    public final Node<T> add(@Nonnull Composite<T> child) {
      children.add(child);
      return this;
    }

    @Override
    public final void execute(@Nonnull Consumer<T> action) {
      children.forEach(child -> child.execute(action));
    }
  }

  private static final class LeafImpl<T> implements Leaf<T> {

    @Nullable
    private T value;

    LeafImpl(@Nullable T value) {
      this.value = value;
    }

    @Nullable
    @Override
    public final T getValue() {
      return value;
    }

    @Override
    public final void setValue(@Nullable T value) {
      this.value = value;
    }

    @Override
    public final void execute(@Nonnull Consumer<T> action) {
      action.accept(value);
    }
  }
}
