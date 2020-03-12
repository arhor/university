package by.arhor.university.service.dto;

import by.arhor.university.Constants;
import lombok.Data;

@Data
public final class UserDTO implements DTO {

  private static final long serialVersionUID = Constants.SERIAL_VERSION;

  private Long   id;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String role;
  private String lang;
}
