package by.arhor.university.service.dto;

import java.util.Objects;
import java.util.StringJoiner;

public final class FacultyDTO implements DTO<Long> {

  private Long id;
  private String defaultTitle;
  private Short seatsPaid;
  private Short seatsBudget;

  public FacultyDTO() {}

  public FacultyDTO(Long id, String defaultTitle, Short seatsPaid, Short seatsBudget) {
    this.id = id;
    this.defaultTitle = defaultTitle;
    this.seatsPaid = seatsPaid;
    this.seatsBudget = seatsBudget;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDefaultTitle() {
    return defaultTitle;
  }

  public void setDefaultTitle(String defaultTitle) {
    this.defaultTitle = defaultTitle;
  }

  public Short getSeatsPaid() {
    return seatsPaid;
  }

  public void setSeatsPaid(Short seatsPaid) {
    this.seatsPaid = seatsPaid;
  }

  public Short getSeatsBudget() {
    return seatsBudget;
  }

  public void setSeatsBudget(Short seatsBudget) {
    this.seatsBudget = seatsBudget;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FacultyDTO that = (FacultyDTO) o;
    return Objects.equals(id, that.id)
        && Objects.equals(defaultTitle, that.defaultTitle)
        && Objects.equals(seatsPaid, that.seatsPaid)
        && Objects.equals(seatsBudget, that.seatsBudget);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        defaultTitle,
        seatsPaid,
        seatsBudget);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", FacultyDTO.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("defaultTitle='" + defaultTitle + "'")
        .add("seatsPaid=" + seatsPaid)
        .add("seatsBudget=" + seatsBudget)
        .toString();
  }
}
