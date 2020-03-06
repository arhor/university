package by.arhor.university.service.dto;

import by.arhor.university.Constants;
import lombok.Data;

import java.util.Objects;
import java.util.StringJoiner;

@Data
public final class FacultyDTO implements DTO {

  private static final long serialVersionUID = Constants.SERIAL_VERSION;

  private Long   id;
  private String defaultTitle;
  private Short  seatsPaid;
  private Short  seatsBudget;
}
