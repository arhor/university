package by.arhor.core.pattern.observer;

public interface ObservableNum<T extends Number> extends Observable<T> {

  void set(T value);

  T get();

}
