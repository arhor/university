package by.arhor.core.pattern.observer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ObservableRef<T> extends Observable<T> {

  <V> void mutate(BiConsumer<T, V> setter, V value);

  <V> Consumer<V> buildMutator(BiConsumer<T, V> setter);

  <V> V access(Function<T, V> getter);

  <V> Supplier<V> buildAccessor(Function<T, V> getter);
}
