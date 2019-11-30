package by.bsu.uir.university.service.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserNotFoundException extends EntityNotFoundException {

  public UserNotFoundException(String fieldName, Object fieldValue) {
    super(ErrorLabel.NOT_FOUND_USER, fieldName, fieldValue);
  }

}
