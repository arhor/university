package by.arhor.university.service.error;

public class UserNotFoundException extends EntityNotFoundException {

  public UserNotFoundException(String fieldName, Object fieldValue) {
    super(ErrorLabel.NOT_FOUND_USER, fieldName, fieldValue);
  }

}
