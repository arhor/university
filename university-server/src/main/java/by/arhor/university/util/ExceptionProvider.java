package by.arhor.university.util;

import by.arhor.university.service.dto.UserDTO;
import by.arhor.university.service.error.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ExceptionProvider {

  private ExceptionProvider() {}

  private static final Map<Class<?>, BiFunction<String, Object, Supplier<? extends Throwable>>> NOT_FOUND;

  static {
    NOT_FOUND = new HashMap<>();
    NOT_FOUND.put(UserDTO.class, (field, value) -> () -> new UserNotFoundException(field, value));
  }

  public static BiFunction<String, Object, Supplier<? extends Throwable>> notFound(Class<?> dtoClass) {
    return NOT_FOUND.get(dtoClass);
  }

}
