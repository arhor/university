package by.arhor.core;

import java.util.function.ToIntFunction;

public final class NumberUtils {

  private NumberUtils() {}

  public static <N extends Number> ToIntFunction<N> minBound(final int bound) {
    return (arg) ->
        ((arg == null) || (arg.intValue() <= bound))
            ? bound
            : arg.intValue();
  }

  public static <N extends Number> ToIntFunction<N> maxBound(final int bound) {
    return (arg) ->
        ((arg == null) || (arg.intValue() <= 0) || (arg.intValue() >= bound))
            ? bound
            : arg.intValue();
  }

}
