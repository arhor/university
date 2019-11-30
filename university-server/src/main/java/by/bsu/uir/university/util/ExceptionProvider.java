package by.bsu.uir.university.util;

import by.bsu.uir.university.service.dto.UserDto;
import by.bsu.uir.university.service.error.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ExceptionProvider {

  private ExceptionProvider() {}

  private static final Map<Class<?>, BiFunction<String, Object, Supplier<? extends Throwable>>> NOT_FOUND;

  static {
    NOT_FOUND = new HashMap<>();
    NOT_FOUND.put(UserDto.class, (field, value) -> () -> new UserNotFoundException(field, value));
  }

  public static BiFunction<String, Object, Supplier<? extends Throwable>> notFound(Class<?> dtoClass) {
    return NOT_FOUND.get(dtoClass);
  }

}
