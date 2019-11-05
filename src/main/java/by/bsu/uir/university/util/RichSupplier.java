package by.bsu.uir.university.util;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface RichSupplier<T> extends Supplier<T> {

  default <R> RichSupplier<R> map(Function<T, R> f) {
    return () -> f.apply(this.get());
  }

  default <R> RichSupplier<R> flatMap(Function<T, Lazy<R>> f) {
    return () -> f.apply(this.get()).get();
  }

  default <R, U> RichSupplier<U> merge(Lazy<R> that, BiFunction<T, R, U> f) {
    return () -> f.apply(this.get(), that.get());
  }

  default void forEach(Consumer<T> action) {
    action.accept(get());
  }

}
