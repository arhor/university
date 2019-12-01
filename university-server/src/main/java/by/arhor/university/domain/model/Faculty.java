package by.arhor.university.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "faculties")
public class Faculty extends AbstractModelObject<Long> {

  @NotEmpty
  @Column(name = "default_title", nullable = false, unique = true, length = 64)
  private String defaultTitle;

  @Min(0)
  @Column(name = "seats_paid", nullable = false)
  private short seatsPaid;

  @Min(0)
  @Column(name = "seats_budget", nullable = false)
  private short seatsBudget;

  @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FacultyTitle> facultyTitles;

  @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FacultyEnrollee> facultyEnrollees;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "faculties_has_subjects",
      joinColumns = @JoinColumn(name = "faculty_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "subject_id", nullable = false))
  private List<Subject> subjects;

  public String getDefaultTitle() {
    return defaultTitle;
  }

  public void setDefaultTitle(String defaultTitle) {
    this.defaultTitle = defaultTitle;
  }

  public short getSeatsPaid() {
    return seatsPaid;
  }

  public void setSeatsPaid(short seatsPaid) {
    this.seatsPaid = seatsPaid;
  }

  public short getSeatsBudget() {
    return seatsBudget;
  }

  public void setSeatsBudget(short seatsBudget) {
    this.seatsBudget = seatsBudget;
  }

  public List<FacultyTitle> getFacultyTitles() {
    return facultyTitles;
  }

  public void setFacultyTitles(List<FacultyTitle> facultyTitles) {
    this.facultyTitles = facultyTitles;
  }

  public List<FacultyEnrollee> getFacultyEnrollees() {
    return facultyEnrollees;
  }

  public void setFacultyEnrollees(List<FacultyEnrollee> facultyEnrollees) {
    this.facultyEnrollees = facultyEnrollees;
  }

  public List<Subject> getSubjects() {
    return subjects;
  }

  public void setSubjects(List<Subject> subjects) {
    this.subjects = subjects;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Faculty faculty = (Faculty) o;
    return seatsPaid == faculty.seatsPaid &&
        seatsBudget == faculty.seatsBudget &&
        Objects.equals(defaultTitle, faculty.defaultTitle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(defaultTitle, seatsPaid, seatsBudget);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Faculty.class.getSimpleName() + "[", "]")
        .add("defaultTitle='" + defaultTitle + "'")
        .add("seatsPaid=" + seatsPaid)
        .add("seatsBudget=" + seatsBudget)
        .toString();
  }
}
