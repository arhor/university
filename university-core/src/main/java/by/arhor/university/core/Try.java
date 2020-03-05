package by.arhor.university.core;

public interface Try<T> {

  T get();

  boolean isFailure();

}
