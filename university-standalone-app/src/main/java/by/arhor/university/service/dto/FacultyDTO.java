package by.arhor.university.service.dto;

import by.arhor.university.Constants;
import lombok.Data;

@Data
public final class FacultyDTO implements DTO {

  private static final long serialVersionUID = Constants.SERIAL_VERSION;

  private Long   id;
  private String defaultTitle;
  private Short  seatsPaid;
  private Short  seatsBudget;
}
