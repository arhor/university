package by.arhor.university.service.error;

public interface ServiceError {

  String NOT_FOUND = "error.not.found";

  ErrorLabel getErrorLabel();

  Object[] props();

  static ServiceError notFound(String ResourceName, String fieldName, Object fieldValue) {
    return new ServiceError_Resource(ErrorLabel.NOT_FOUND, ResourceName, fieldName, fieldValue);
  }

  static ServiceError alreadyExists(String ResourceName, String fieldName, Object fieldValue) {
    return new ServiceError_Resource(ErrorLabel.ALREADY_EXISTS, ResourceName, fieldName, fieldValue);
  }

  class ServiceError_Resource implements ServiceError {

    private final ErrorLabel errorLabel;
    private final String ResourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ServiceError_Resource(ErrorLabel errorLabel, String ResourceName, String fieldName, Object fieldValue) {
      this.errorLabel = errorLabel;
      this.ResourceName = ResourceName;
      this.fieldName = fieldName;
      this.fieldValue = fieldValue;
    }

    @Override
    public Object[] props() {
      return new Object[] {ResourceName, fieldName, fieldValue};
    }

    @Override
    public ErrorLabel getErrorLabel() {
      return errorLabel;
    }

    public String getResourceName() {
      return ResourceName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public Object getFieldValue() {
      return fieldValue;
    }
  }

}
