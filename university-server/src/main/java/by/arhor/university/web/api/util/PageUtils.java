package by.arhor.university.web.api.util;

import java.util.function.ToIntFunction;

import by.arhor.core.IntBiFunction;
import by.arhor.core.util.NumberUtils;

public final class PageUtils {

  public static final int DEFAULT_PAGE = 1;
  public static final int DEFAULT_SIZE = 50;

  private static final ToIntFunction<Number> BOUNDED_PAGE;
  private static final ToIntFunction<Number> BOUNDED_SIZE;

  static {
    BOUNDED_PAGE = NumberUtils.minBound(DEFAULT_PAGE);
    BOUNDED_SIZE = NumberUtils.maxBound(DEFAULT_SIZE);
  }

  private PageUtils() {}

  public static <T> IntBiFunction<T> paginate(IntBiFunction<T> request) {
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
