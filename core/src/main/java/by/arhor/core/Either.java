package by.arhor.core;

import java.util.function.Function;

import javax.annotation.Nonnull;

public final class Either<T, E> {

  private final T item;
  private final E error;

  private Either(T item, E error) {
    this.item = item;
    this.error = error;
  }

  public static <T, E> Either<T, E> success(T item) {
    return new Either<>(item, null);
  }

  public static <T, E> Either<T, E> error(E error) {
    return new Either<>(null, error);
  }

  public final boolean hasError() {
    return error != null;
  }

  public final T getItem() {
    if (hasError()) {
      throw new IllegalStateException("Must not extract item if error occurred");
    }
    return item;
  }

  public final E getError() {
    return error;
  }

  public <R> Either<R, E> map(@Nonnull Function<T, R> mapper) {
    return new Either<>(hasError() ? null : mapper.apply(item), error);
  }
}
