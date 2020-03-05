package by.arhor.university.core.pattern.observer;

public interface ObservableInt extends ObservableVal<Integer> {

  void setValue(int value);

  int getValue();
}
