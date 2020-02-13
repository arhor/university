package by.arhor.university.service.error;

public enum ErrorLabel {

  UNKNOWN            (""),

  NOT_FOUND          ("error.not.found"),

  NOT_FOUND_USER     ("error.not.found.user"),
  NOT_FOUND_ENROLLEE ("error.not.found.enrollee"),
  NOT_FOUND_SUBJECT  ("error.not.found.subject"),
  NOT_FOUND_FACULTY  ("error.not.found.faculty"),
  NOT_FOUND_LANG     ("error.not.found.lang"),
  NOT_FOUND_ROLE     ("error.not.found.role"),

  ALREADY_ENROLLED   ("error.already.enrolled");

  private final String value;

  ErrorLabel(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

}
