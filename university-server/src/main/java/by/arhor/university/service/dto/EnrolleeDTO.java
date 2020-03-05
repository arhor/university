package by.arhor.university.service.dto;

import lombok.Data;

@Data
public final class EnrolleeDTO implements DTO<Long> {
  private Long   id;
  private String country;
  private String city;
  private Byte   schoolScore;
}
