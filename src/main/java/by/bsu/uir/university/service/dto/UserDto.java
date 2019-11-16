package by.bsu.uir.university.service.dto;

import lombok.Data;

@Data
public class UserDto implements Dto {

  private Long   id;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String role;
  private String lang;

}
