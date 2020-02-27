package by.arhor.core.pattern.observer;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

final class ObservableFactory {

  private ObservableFactory() {
    throw new UnsupportedOperationException("Must not be instantiated");
  }

  static <V> ObservableVal<V> observableVal(V value) {
    return new ObservableValImpl<>(value);
  }

  static <R> ObservableRef<R> observableRef(R reference) {
    return new ObservableRefImpl<>(reference);
  }

  private abstract static class AbstractObservable<T> implements Observable<T> {
    private final Set<Observer<T>> observers = new LinkedHashSet<>();
    protected T observable;

    AbstractObservable(T observable) {
      this.observable = observable;
    }

    @Override
    public final void subscribe(Observer<T> observer) {
      observers.add(observer);
    }

    @Override
    public final void unsubscribe(Observer<T> observer) {
      observers.remove(observer);
    }

    protected final void noticeObservers() {
      observers.forEach(observer -> observer.notice(observable));
    }
  }

  private static final class ObservableValImpl<V> extends AbstractObservable<V> implements ObservableVal<V> {
    ObservableValImpl(V value) { super(value); }

    @Override
    public final void set(V value) {
      observable = value;
      noticeObservers();
    }

    @Override
    public final V get() {
      return observable;
    }
  }

  private static final class ObservableRefImpl<R> extends AbstractObservable<R> implements ObservableRef<R> {
    ObservableRefImpl(R reference) { super(reference); }

    @Override
    public final <V> void mutate(BiConsumer<R, V> setter, V value) {
      setter.accept(observable, value);
      noticeObservers();
    }

    @Override
    public final <V> V access(Function<R, V> getter) {
      return getter.apply(observable);
    }

    @Override
    public final <V> Consumer<V> buildSetter(BiConsumer<R, V> setter) {
      return (value) -> {
        setter.accept(observable, value);
        noticeObservers();
      };
    }

    @Override
    public final <V> Supplier<V> buildGetter(Function<R, V> getter) {
      return () -> getter.apply(observable);
    }
  }
}
