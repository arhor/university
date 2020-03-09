package by.arhor.university.web.api.v1;

import by.arhor.university.core.Either;
import by.arhor.university.core.Pair;
import by.arhor.university.service.error.ErrorLabel;
import by.arhor.university.service.error.ServiceError;
import by.arhor.university.web.api.model.ApiError;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

@Slf4j
public abstract class ApiController {

  private static final Pair<HttpStatus,String> DEFAULT_ERROR_RESPONSE = Pair.of(HttpStatus.INTERNAL_SERVER_ERROR, ApiError.UNEXPECTED);

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
    log.debug("success!");
    return ResponseEntity.ok(item);
  }

  private ResponseEntity<ApiError> handleFailure(ServiceError error, Locale locale) {
    var errorLabel = error.getErrorLabel();

    log.debug("failure!");

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
      return switch (errorLabel) {
        case NOT_FOUND -> Pair.of(HttpStatus.NOT_FOUND, ApiError.NOT_FOUND);
        default -> DEFAULT_ERROR_RESPONSE;
      };
    } else {
      return DEFAULT_ERROR_RESPONSE;
    }
  }
}
