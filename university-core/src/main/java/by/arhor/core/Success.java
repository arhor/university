package by.arhor.core;

public final class Success<T> extends AbstractTry<T> {

  public Success(T value) { super(value); }

  @Override
  public final boolean isFailure() {
    return false;
  }
}
