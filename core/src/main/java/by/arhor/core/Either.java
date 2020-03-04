package by.arhor.core;

import java.util.function.Function;

import javax.annotation.Nonnull;

public final class Either<T, E> {

  private static final Either<Void, ?> SUCCESS = new Either();

  private final T item;
  private final E error;

  private Either(T item, E error) {
    this.item = item;
    this.error = error;
  }

  private Either() {
    this(null, null);
  }

  @SuppressWarnings("unchecked")
  public static <E> Either<Void, E> success() {
    return (Either<Void, E>) SUCCESS;
  }

  public static <T, E> Either<T, E> success(T item) {
    return new Either<>(item, null);
  }

  public static <T, E> Either<T, E> failure(E error) {
    return new Either<>(null, error);
  }

  public final boolean hasError() {
    return error != null;
  }

  public final T getItem() {
    if (hasError()) {
      throw new IllegalStateException("Must not extract item in error state");
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
