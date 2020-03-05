package by.arhor.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

public class CoreUtilTest {

  @Test
  public void memorizeTest() {
    final Function<Integer, String> func = String::valueOf;
    final Function<Integer, String> memorizedFunc = CoreUtils.memorize(func);

    final var value = 999;

    assertEquals(
        func.apply(value),
        memorizedFunc.apply(value)
    );
  }

  @Test
  public void forEachTest_plain() {
    CoreUtils
        .forEach(1, 2, 3)
        .accept(System.out::println);
  }

  @Test
  public void forEachTest_iterable() {
    CoreUtils
        .forEach(
            List.of(1, 2, 3),
            Set.of(4, 5, 6))
        .accept(System.out::println);
  }

  @Test
  public void forEachTest_array() {
    CoreUtils
        .forEach(
            new byte[]    { 1,  2,  3  })
        .accept(System.out::println);
  }

}
