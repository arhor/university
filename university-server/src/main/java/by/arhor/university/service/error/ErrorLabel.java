package by.arhor.university.service.error;

public enum ErrorLabel {

  UNKNOWN            ("error.unexpected"),
  NOT_FOUND          ("error.not.found"),
  ALREADY_EXISTS     ("error.already.exists");

  private final String value;

  ErrorLabel(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
