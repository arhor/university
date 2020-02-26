package by.arhor.core.pattern.observer;

import javax.annotation.Nonnull;

public interface Observable<T> {

  void subscribe(Observer<T> observer);

  void unsubscribe(Observer<T> observer);

  static <N extends Number> ObservableNum<N> of(@Nonnull N value) {
    return ObservableFactory.observableNumber(value);
  }

  static <T> ObservableRef<T> of(@Nonnull T value) {
    return ObservableFactory.observableReference(value);
  }
}
