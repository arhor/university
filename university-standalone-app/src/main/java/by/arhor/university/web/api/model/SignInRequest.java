package by.arhor.university.web.api.model;

import lombok.Data;

@Data
public class SignInRequest {
  private String email;
  private String password;
}
