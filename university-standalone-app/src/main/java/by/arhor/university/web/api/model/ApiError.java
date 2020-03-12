package by.arhor.university.web.api.model;

import java.io.Serializable;

import by.arhor.university.Constants;
import lombok.Value;

@Value
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
}
