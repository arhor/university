//package by.arhor.core.util;
//
//import static java.lang.String.format;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.Random;
//import java.util.concurrent.ThreadLocalRandom;
//import java.util.function.ToIntFunction;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
//
//public class NumberUtilsTest {
//
//  private static final Logger log = LoggerFactory.getLogger(NumberUtilsTest.class);
//
//  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//  public static class BoundTest {
//
//    private static int min;
//    private static int max;
//
//    private static ToIntFunction<Number> minBound;
//    private static ToIntFunction<Number> maxBound;
//
//    @BeforeAll
//    public void setup() {
//      final var generator = ThreadLocalRandom.current();
//
//      min = generator.nextInt();
//      max = generator.nextInt();
//
//      minBound = NumberUtils.minBound(min);
//      maxBound = NumberUtils.maxBound(max);
//
//      log.info(() -> format("Starting bound tests with initial values: min = %d, max = %d", min, max));
//    }
//
//    @AfterAll
//    public void teardown() {
//      minBound = null;
//      maxBound = null;
//      log.info(() -> "Bound tests finished");
//    }
//
//    @Test
//    public void minBound_lessThanBoundTest() {
//      assertEquals(
//          min,
//          minBound.applyAsInt(min - 1)
//      );
//    }
//
//    @Test
//    public void minBound_equalBoundTest() {
//      assertEquals(
//          min,
//          minBound.applyAsInt(min)
//      );
//    }
//
//    @Test
//    public void minBound_greaterThanBoundTest() {
//      assertTrue(
//          min < minBound.applyAsInt(min + 1)
//      );
//    }
//
//    @Test
//    public void maxBound_lessThanBoundTest() {
//      assertTrue(
//          max > maxBound.applyAsInt(max - 1)
//      );
//    }
//
//    @Test
//    public void maxBound_equalBoundTest() {
//      assertEquals(
//          max,
//          maxBound.applyAsInt(min)
//      );
//    }
//
//    @Test
//    public void maxBound_greaterThanBoundTest() {
//      assertEquals(
//          max,
//          maxBound.applyAsInt(min + 1)
//      );
//    }
//
//  }
//
//}
