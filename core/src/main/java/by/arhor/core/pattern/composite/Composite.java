package by.arhor.core.pattern.composite;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

public interface Composite<T> {

  void execute(@Nonnull Consumer<T> action);
}
