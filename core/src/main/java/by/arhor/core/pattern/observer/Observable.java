package by.arhor.core.pattern.observer;

import javax.annotation.Nonnull;

public interface Observable<T> {

  void subscribe(Observer<T> observer);

  void unsubscribe(Observer<T> observer);

  static <T> ObservableRef<T> ofReference(@Nonnull T value) {
    return ObservableFactory.observableRef(value);
  }

  static <T> ObservableVal<T> ofValue(@Nonnull T value) {
    return ObservableFactory.observableVal(value);
  }

  static <T extends Number & Comparable<T>> ObservableVal<T> of(@Nonnull T value) {
    return ofValue(value);
  }

  static ObservableVal<Boolean> of(@Nonnull Boolean value) {
    return ofValue(value);
  }

  static ObservableVal<Character> of(@Nonnull Character value) {
    return ofValue(value);
  }

  static ObservableVal<String> of(@Nonnull String value) {
    return ofValue(value);
  }

  static <T> ObservableRef<T> of(@Nonnull T value) {
    return ofReference(value);
  }
}
