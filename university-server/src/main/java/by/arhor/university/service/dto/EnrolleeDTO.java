package by.arhor.university.service.dto;

import java.util.Objects;
import java.util.StringJoiner;

public final class EnrolleeDTO implements DTO {

  private Long id;
  private String country;
  private String city;
  private Byte schoolScore;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Byte getSchoolScore() {
    return schoolScore;
  }

  public void setSchoolScore(Byte schoolScore) {
    this.schoolScore = schoolScore;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EnrolleeDTO that = (EnrolleeDTO) o;
    return Objects.equals(id, that.id)
        && Objects.equals(country, that.country)
        && Objects.equals(city, that.city)
        && Objects.equals(schoolScore, that.schoolScore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, country, city, schoolScore);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", EnrolleeDTO.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("country='" + country + "'")
        .add("city='" + city + "'")
        .add("schoolScore=" + schoolScore)
        .toString();
  }
}
