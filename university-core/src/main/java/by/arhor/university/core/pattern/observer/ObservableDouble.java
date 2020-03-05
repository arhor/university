package by.arhor.university.core.pattern.observer;

public interface ObservableDouble extends ObservableVal<Double> {

  double getValue();

  void setValue(double value);
}
