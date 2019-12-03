package by.arhor.core.util;

import javax.annotation.Nonnull;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public final class CoreUtils {

  private CoreUtils() {}

  @Nonnull
  public static
  <T, R> Function<T, R> memorize(@Nonnull final Function<T, R> source) {
    final var ctx = new ConcurrentHashMap<T, R>();
    return (arg) -> ctx.computeIfAbsent(arg, source);
  }

}
