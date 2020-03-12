package by.arhor.university.web.api.model;

import lombok.Data;

@Data
public class SignUpRequest {
  private String email;
  private String password;
  private String firstName;
  private String lastName;
}
