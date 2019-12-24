package by.arhor.university.service.error;

public abstract class EntityNotFoundException extends RuntimeException {

  private final ErrorLabel errorLabel;
  private final String fieldName;
  private final Object fieldValue;

  protected EntityNotFoundException(ErrorLabel errorLabel, String fieldName, Object fieldValue) {
    this.errorLabel = errorLabel;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public ErrorLabel getErrorLabel() {
    return errorLabel;
  }

  public String getFieldName() {
    return fieldName;
  }

  public Object getFieldValue() {
    return fieldValue;
  }
}
