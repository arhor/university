package by.arhor.core;

public interface Try<T> {

  T get();

  boolean isFailure();

}
