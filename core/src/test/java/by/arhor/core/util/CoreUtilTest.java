package by.arhor.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
