package by.bsu.uir.university.service.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorLabel {

  NOT_FOUND_USER     ("error.not.found.user"),
  NOT_FOUND_ENROLLEE ("error.not.found.enrollee"),
  NOT_FOUND_SUBJECT  ("error.not.found.subject"),
  NOT_FOUND_FACULTY  ("error.not.found.faculty"),
  NOT_FOUND_LANG     ("error.not.found.lang"),
  NOT_FOUND_ROLE     ("error.not.found.role");

  @Getter
  private final String value;
}
