package by.arhor.core.pattern.observer;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface ObservableRef<T> extends Observable<T> {

  <V> void mutate(BiConsumer<T, V> setter, V value);

  <V> V access(Function<T, V> getter);
}
