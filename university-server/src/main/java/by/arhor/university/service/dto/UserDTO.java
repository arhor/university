package by.arhor.university.service.dto;

import lombok.Data;

@Data
public final class UserDTO implements DTO<Long> {
  private Long id;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String role;
  private String lang;
}
