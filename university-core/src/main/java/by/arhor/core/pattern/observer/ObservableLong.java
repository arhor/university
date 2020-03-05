package by.arhor.core.pattern.observer;

public interface ObservableLong extends ObservableVal<Long> {

  long getValue();

  void setValue(long value);
}
