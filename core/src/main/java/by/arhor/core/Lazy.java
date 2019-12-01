package by.arhor.core;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class Lazy<T> implements RichSupplier<T> {

  private Supplier<T> source;
  protected boolean computed;
  protected T value;

  private Lazy(final Supplier<T> source) {
    Objects.requireNonNull(source, "Lazy evaluation source must not be null");
    this.source = source;
  }

  public static <T> Lazy<T> eval(final Supplier<T> source) {
    return new Lazy<>(source);
  }

  public static <T> Lazy<T> evalSafe(final Supplier<T> source) {
    return new Lazy<>(source) {
      @Override
      public T get() {
        if (!computed) {
          synchronized (this) {
            compute();
          }
        }
        return value;
      }

      @Override
      public <R> Lazy<R> map(final Function<T, R> f) {
        return evalSafe(() -> f.apply(this.get()));
      }
    };
  }

  @Override
  public T get() {
    compute();
    return value;
  }

  protected final void compute() {
    if (!computed) {
      value = source.get();
      // Nullifying source reference allows to release associated scope and prevent memory leaks
      source = null;
      computed = true;
    }
  }

  public final boolean isComputed() {
    return computed;
  }

  @Override
  public <R> Lazy<R> map(final Function<T, R> f) {
    return eval(() -> f.apply(this.get()));
  }

}
