package by.arhor.university.web.api.v1;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import by.arhor.core.Either;
import by.arhor.university.service.error.ServiceError;
import by.arhor.university.web.api.model.ApiError;

public class ApiController {

  protected static final String API_V_1 = "/api/v1";

  private static final Logger log = LoggerFactory.getLogger(ApiController.class);

  @Autowired private MessageSource messageSource;

  protected <T> ResponseEntity<?> handle(Either<T, ServiceError> either, Locale locale) {
    return either.hasError()
        ? handleError(either.getError(), locale)
        : handleSuccess(either.getItem());
  }

  private <T> ResponseEntity<T> handleSuccess(T item) {
    return ResponseEntity.ok(item);
  }

  private ResponseEntity<ApiError> handleError(ServiceError error, Locale locale) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            new ApiError(
                ApiError.NOT_FOUND,
                messageSource.getMessage(
                    error.getErrorLabel().getValue(),
                    new Object[] {error.getFieldName(), error.getFieldValue()},
                    locale)));
  }
}
