package by.arhor.university.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "faculties_has_enrollees")
public class FacultyEnrollee {

  @EmbeddedId
  private CompositeId id;

  @Column(name = "filing_date", nullable = false, updatable = false)
  private LocalDateTime filingDate;

  @ManyToOne(optional = false)
  @MapsId("faculty_id")
  private Faculty faculty;

  @ManyToOne(optional = false)
  @MapsId("enrollee_id")
  private Enrollee enrollee;

  @PrePersist
  public void logFilingDate() {
    this.filingDate = LocalDateTime.now();
  }

  public CompositeId getId() {
    return id;
  }

  public void setId(CompositeId id) {
    this.id = id;
  }

  public LocalDateTime getFilingDate() {
    return filingDate;
  }

  public void setFilingDate(LocalDateTime filingDate) {
    this.filingDate = filingDate;
  }

  public Faculty getFaculty() {
    return faculty;
  }

  public void setFaculty(Faculty faculty) {
    this.faculty = faculty;
  }

  public Enrollee getEnrollee() {
    return enrollee;
  }

  public void setEnrollee(Enrollee enrollee) {
    this.enrollee = enrollee;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FacultyEnrollee that = (FacultyEnrollee) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(filingDate, that.filingDate) &&
        Objects.equals(faculty, that.faculty) &&
        Objects.equals(enrollee, that.enrollee);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, filingDate, faculty, enrollee);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", FacultyEnrollee.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("filingDate=" + filingDate)
        .add("faculty=" + faculty)
        .add("enrollee=" + enrollee)
        .toString();
  }

  @Embeddable
  public static class CompositeId implements Serializable {
    @Column(name = "faculty_id", nullable = false)
    private Long facultyId;

    @Column(name = "enrollee_id", nullable = false)
    private Long enrolleeId;

    public CompositeId() {
    }

    public CompositeId(Long facultyId, Long enrolleeId) {
      this.facultyId = facultyId;
      this.enrolleeId = facultyId;
    }

    public Long getFacultyId() {
      return facultyId;
    }

    public void setFacultyId(Long facultyId) {
      this.facultyId = facultyId;
    }

    public Long getEnrolleeId() {
      return enrolleeId;
    }

    public void setEnrolleeId(Long enrolleeId) {
      this.enrolleeId = enrolleeId;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CompositeId that = (CompositeId) o;
      return Objects.equals(facultyId, that.facultyId) &&
          Objects.equals(enrolleeId, that.enrolleeId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(facultyId, enrolleeId);
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", CompositeId.class.getSimpleName() + "[", "]")
          .add("facultyId=" + facultyId)
          .add("enrolleeId=" + enrolleeId)
          .toString();
    }
  }
}
