package by.arhor.university.web.api.v1;

import by.arhor.university.core.Either;
import by.arhor.university.core.Pair;
import by.arhor.university.service.error.ErrorLabel;
import by.arhor.university.service.error.ServiceError;
import by.arhor.university.web.api.model.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public abstract class ApiController {

  private static final Pair<HttpStatus,String> DEFAULT_ERROR_RESPONSE = Pair.of(HttpStatus.INTERNAL_SERVER_ERROR, ApiError.UNEXPECTED);
  private static final Logger log = LoggerFactory.getLogger(ApiController.class);

  @Autowired private MessageSource messageSource;

  protected <T> ResponseEntity<?> handle(Either<T, ServiceError> either, Locale locale) {
    log.debug("handling service response");
    if (either == null) {
      log.debug("expected response is [Either], buy service responded with [null]");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("null");
    }
    return either.hasError()
        ? handleFailure(either.error(), locale)
        : handleSuccess(either.value().orElse(null));
  }

  private <T> ResponseEntity<T> handleSuccess(T item) {
    log.debug("handling ");
    return ResponseEntity.ok(item);
  }

  private ResponseEntity<ApiError> handleFailure(ServiceError error, Locale locale) {
    var errorLabel = error.getErrorLabel();

    log.debug("handling failure with error label: [{}]", errorLabel);

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
