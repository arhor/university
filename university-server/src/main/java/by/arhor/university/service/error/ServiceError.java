package by.arhor.university.service.error;

public class ServiceError {

  private final ErrorLabel errorLabel;
  private final String fieldName;
  private final Object fieldValue;

  public ServiceError(ErrorLabel errorLabel, String fieldName, Object fieldValue) {
    this.errorLabel = errorLabel;
    this.fieldName = fieldName;
    this.fieldValue = fieldValue;
  }

  public static ServiceError notFound() {
    return null;
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
