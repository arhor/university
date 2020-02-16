package by.arhor.university.web.api.model;

import by.arhor.university.Constants;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@SuppressWarnings({"unused"})
public final class ApiError implements Serializable {

  private static final long serialVersionUID = Constants.SERIAL_VERSION;

  public static final String UNEXPECTED           = "10001";
  public static final String NOT_FOUND            = "10001";
  public static final String WRONG_METHOD_PARAMS  = "10002";
  public static final String TYPE_UNSUPPORTED     = "10003";
  public static final String TYPE_NOT_ACCEPTED    = "10004";
  public static final String METHOD_UNSUPPORTED   = "10005";
  public static final String INVALID_JSON_MESSAGE = "10006";

  private final String code;
  private final String message;

  public ApiError(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiError apiError = (ApiError) o;
    return Objects.equals(code, apiError.code)
        && Objects.equals(message, apiError.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ApiError.class.getSimpleName() + "[", "]")
        .add("code='" + code + "'")
        .add("message='" + message + "'")
        .toString();
  }
}
