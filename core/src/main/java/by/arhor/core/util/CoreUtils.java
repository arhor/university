package by.arhor.core.util;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public final class CoreUtils {

  private CoreUtils() {}

  public static <T, R> Function<T, R> memorize(final Function<T, R> source) {
    Objects.requireNonNull(source, "Calculation source must not be null");
    final var ctx = new ConcurrentHashMap<T, R>();
    return (arg) -> ctx.computeIfAbsent(arg, source);
  }

}
