package by.arhor.university.core.util;

import static by.arhor.university.core.util.StringUtils.JsonBuilder.json;

import org.junit.jupiter.api.Test;

public class StringUtilsTest {

  @Test
  public void test() {
    var json = json()
        .prop("name", "Max")
        .prop("age", 30)
        .prop("inner", json()
            .prop("deduction", "big")
            .prop("amount", 123)
        ).build();
    System.out.println(json);
  }
}
