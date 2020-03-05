package by.arhor.university.core.pattern.observer;

public interface ObservableByte extends ObservableVal<Byte> {

  byte getValue();

  void setValue(byte value);
}
