package by.arhor.university.service.dto;

import by.arhor.university.Constants;
import lombok.Data;

@Data
public final class EnrolleeSubjectDTO implements DTO {

  private static final long serialVersionUID = Constants.SERIAL_VERSION;

  private SubjectDTO subject;
  private Short score;
}
