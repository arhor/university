package by.arhor.university.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "enrollees", schema = "dbo")
public class Enrollee extends AbstractModelObject<Long> {

  @NotEmpty
  @Column(name = "country", nullable = false, length = 64)
  private String country;

  @NotEmpty
  @Column(name = "city", nullable = false, length = 64)
  private String city;

  @Min(0)
  @Max(100)
  @Column(name = "school_score", nullable = false)
  private byte schoolScore;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private User user;

  @OneToMany(mappedBy = "enrollee", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EnrolleeSubject> enrolleeSubjects;

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

  public byte getSchoolScore() {
    return schoolScore;
  }

  public void setSchoolScore(byte schoolScore) {
    this.schoolScore = schoolScore;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<EnrolleeSubject> getEnrolleeSubjects() {
    return enrolleeSubjects;
  }

  public void setEnrolleeSubjects(List<EnrolleeSubject> enrolleeSubjects) {
    this.enrolleeSubjects = enrolleeSubjects;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Enrollee enrollee = (Enrollee) o;
    return schoolScore == enrollee.schoolScore &&
        Objects.equals(country, enrollee.country) &&
        Objects.equals(city, enrollee.city);
  }

  @Override
  public int hashCode() {
    return Objects.hash(country, city, schoolScore);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Enrollee.class.getSimpleName() + "[", "]")
        .add("country='" + country + "'")
        .add("city='" + city + "'")
        .add("schoolScore=" + schoolScore)
        .toString();
  }
}
