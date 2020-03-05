package by.arhor.university.core.pattern.observer;

public interface ObservableVal<T> extends Observable<T> {

  void set(T value);

  T get();

}
