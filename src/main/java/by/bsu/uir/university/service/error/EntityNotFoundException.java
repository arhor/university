package by.bsu.uir.university.service.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class EntityNotFoundException extends RuntimeException {

  private final ErrorLabel errorLabel;
  private final String fieldName;
  private final Object fieldValue;

}
