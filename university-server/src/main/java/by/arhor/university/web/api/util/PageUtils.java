package by.arhor.university.web.api.util;

import by.arhor.core.IntBiFunction;
import by.arhor.core.NumberUtils;

import java.util.function.BiFunction;
import java.util.function.ToIntFunction;

public final class PageUtils {

  private static final int DEFAULT_PAGE = 1;
  private static final int DEFAULT_SIZE = 50;

  private static final ToIntFunction<Number> BOUNDED_PAGE = NumberUtils.minBound(DEFAULT_PAGE);
  private static final ToIntFunction<Number> BOUNDED_SIZE = NumberUtils.maxBound(DEFAULT_SIZE);

  private PageUtils() {}

  public static
  <T> IntBiFunction<T> paginate(IntBiFunction<T> request) {
    return (p, s) -> request.apply(
        boundPage(p),
        boundSize(s));
  }

  private static <N extends Number> int boundPage(N page) {
    return BOUNDED_PAGE.applyAsInt(page);
  }

  private static <N extends Number> int boundSize(N size) {
    return BOUNDED_SIZE.applyAsInt(size);
  }

}
