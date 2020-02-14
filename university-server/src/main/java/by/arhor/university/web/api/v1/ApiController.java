package by.arhor.university.web.api.v1;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import by.arhor.core.Either;
import by.arhor.core.Pair;
import by.arhor.university.service.error.ErrorLabel;
import by.arhor.university.service.error.ServiceError;
import by.arhor.university.web.api.model.ApiError;

public class ApiController {

  protected static final String API_V_1 = "/api/v1";

  private static final Pair<HttpStatus,String> DEFAULT_ERROR_RESPONSE = Pair.of(HttpStatus.INTERNAL_SERVER_ERROR, ApiError.UNEXPECTED);
  private static final Logger log = LoggerFactory.getLogger(ApiController.class);

  @Autowired private MessageSource messageSource;

  protected <T> ResponseEntity<?> handle(Either<T, ServiceError> either, Locale locale) {
    if (either == null) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("null");
    }
    return either.hasError()
        ? handleError(either.getError(), locale)
        : handleSuccess(either.getItem());
  }

  private <T> ResponseEntity<T> handleSuccess(T item) {
    return ResponseEntity.ok(item);
  }

  private ResponseEntity<ApiError> handleError(ServiceError error, Locale locale) {
    var errorLabel = error.getErrorLabel();

    var statusAndCode = parseStatusAndCode(errorLabel);

    return ResponseEntity.status(statusAndCode.getFirst())
        .body(
            new ApiError(
                statusAndCode.getSecond(),
                messageSource.getMessage(
                    errorLabel.getValue(),
                    error.props(),
                    locale)));
  }

  private Pair<HttpStatus, String> parseStatusAndCode(ErrorLabel errorLabel) {
    if (errorLabel != null) {
      switch (errorLabel) {
        case NOT_FOUND:
          return Pair.of(HttpStatus.NOT_FOUND, ApiError.NOT_FOUND);
        default:
          return DEFAULT_ERROR_RESPONSE;
      }
    } else {
      return DEFAULT_ERROR_RESPONSE;
    }
  }
}
