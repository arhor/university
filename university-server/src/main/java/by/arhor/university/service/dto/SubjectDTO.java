package by.arhor.university.service.dto;

import lombok.Data;

@Data
public final class SubjectDTO implements DTO<Long> {
  private Long   id;
  private String title;
}
