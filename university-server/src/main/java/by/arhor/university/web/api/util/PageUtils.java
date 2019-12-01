package by.arhor.university.web.api.util;

import by.arhor.core.IntBiFunction;
import by.arhor.core.NumberUtils;

import java.util.function.BiFunction;

public final class PageUtils {

  private static final int DEFAULT_PAGE = 1;
  private static final int DEFAULT_SIZE = 10;

  private PageUtils() {}

  public static
  <T, N extends Number> BiFunction<N, N, T> paginate(IntBiFunction<T> request) {
    return (p, s) -> request.apply(
        parsePage(p),
        parseSize(s));
  }

  private static <N extends Number> int parsePage(N page) {
    return NumberUtils.minBound(DEFAULT_PAGE).applyAsInt(page);
  }

  private static <N extends Number> int parseSize(N size) {
    return NumberUtils.maxBound(DEFAULT_SIZE).applyAsInt(size);
  }

}
