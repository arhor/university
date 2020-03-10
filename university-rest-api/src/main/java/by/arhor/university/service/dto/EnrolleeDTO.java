package by.arhor.university.service.dto;

import java.util.List;

import by.arhor.university.Constants;
import lombok.Data;

@Data
public final class EnrolleeDTO implements DTO {

  private static final long serialVersionUID = Constants.SERIAL_VERSION;

  private Long   id;
  private String country;
  private String city;
  private Byte   schoolScore;
  private List<EnrolleeSubjectDTO> subjects;
}
