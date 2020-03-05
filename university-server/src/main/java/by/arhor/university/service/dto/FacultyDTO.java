package by.arhor.university.service.dto;

import lombok.Data;

import java.util.Objects;
import java.util.StringJoiner;

@Data
public final class FacultyDTO implements DTO<Long> {
  private Long   id;
  private String defaultTitle;
  private Short  seatsPaid;
  private Short  seatsBudget;
}
