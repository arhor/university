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

  static <N extends Number> ObservableNum<N> observableNumber(N number) {
    class _Observable extends AbstractObservable<N> implements ObservableNum<N> {
      _Observable(N observable) {
        super(observable);
      }

      @Override
      public void set(N value) {
        observable = value;
        noticeObservers();
      }

      @Override
      public N get() {
        return observable;
      }
    }
    return new _Observable(number);
  }

  static <T> ObservableRef<T> observableReference(T value) {
    class _Observable extends AbstractObservable<T> implements ObservableRef<T> {
      _Observable(T observable) {
        super(observable);
      }

      @Override
      public <V> void mutate(BiConsumer<T, V> setter, V value) {
        setter.accept(observable, value);
        noticeObservers();
      }

      @Override
      public <V> Consumer<V> buildMutator(BiConsumer<T, V> setter) {
        return (value) -> {
          setter.accept(observable, value);
          noticeObservers();
        };
      }

      @Override
      public <V> V access(Function<T, V> getter) {
        return getter.apply(observable);
      }

      @Override
      public <V> Supplier<V> buildAccessor(Function<T, V> getter) {
        return () -> getter.apply(observable);
      }
    }
    return new _Observable(value);
  }

  private abstract static class AbstractObservable<T> implements Observable<T> {

    private final Set<Observer<T>> observers = new LinkedHashSet<>();

    protected T observable;

    AbstractObservable(T observable) {
      this.observable = observable;
    }

    @Override
    public void subscribe(Observer<T> observer) {
      observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer<T> observer) {
      observers.remove(observer);
    }

    protected void noticeObservers() {
      observers.forEach(observer -> observer.notice(observable));
    }
  }
}
