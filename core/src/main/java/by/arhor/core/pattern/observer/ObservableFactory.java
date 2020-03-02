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

    protected final Set<Observer<T>> observers = new LinkedHashSet<>();

    @Override
    public final void subscribe(Observer<T> observer) {
      observers.add(observer);
    }

    @Override
    public final void unsubscribe(Observer<T> observer) {
      observers.remove(observer);
    }
  }

  private abstract static class AbstractObservableObject<T> extends AbstractObservable<T> {

    protected T observable;

    AbstractObservableObject(T observable) {
      this.observable = observable;
    }

    protected final void noticeObservers() {
      observers.forEach(observer -> observer.notice(observable));
    }
  }

  private static final class ObservableValImpl<V> extends AbstractObservableObject<V> implements ObservableVal<V> {
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

  private static final class ObservableRefImpl<R> extends AbstractObservableObject<R> implements ObservableRef<R> {
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

  private static final class ObservableIntImpl extends AbstractObservable<Integer> implements ObservableInt {

    private int value;

    @Override
    public void setValue(int value) {
      this.value = value;
    }

    @Override
    public int getValue() {
      return value;
    }

    @Override
    public void set(Integer value) {
      if (value != null) {
        this.value = value;
      }
    }

    @Override
    public Integer get() {
      return value;
    }
  }

  private static final class ObservableBooleanImpl extends AbstractObservable<Boolean> implements ObservableBoolean {

    private boolean value;

    @Override
    public void setValue(boolean value) {
      this.value = value;
    }

    @Override
    public boolean getValue() {
      return value;
    }

    @Override
    public void set(Boolean value) {
      if (value != null) {
        this.value = value;
      }
    }

    @Override
    public Boolean get() {
      return value;
    }
  }
}
