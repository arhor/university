package by.arhor.core;

public class Failure<T extends Throwable> extends AbstractTry<T> {

  public Failure(T value) { super(value); }

  @Override
  public final boolean isFailure() {
    return true;
  }
}
