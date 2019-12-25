package by.arhor.university.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "subjects")
public class Subject extends AbstractModelObject<Long> {

  @NotEmpty
  @Column(name = "default_title", nullable = false, unique = true, length = 64)
  private String defaultTitle;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "faculties_has_subjects",
      joinColumns = @JoinColumn(name = "subject_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "faculty_id", nullable = false))
  private List<Faculty> faculties;

  public String getDefaultTitle() {
    return defaultTitle;
  }

  public void setDefaultTitle(String defaultTitle) {
    this.defaultTitle = defaultTitle;
  }

  public List<Faculty> getFaculties() {
    return faculties;
  }

  public void setFaculties(List<Faculty> faculties) {
    this.faculties = faculties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Subject subject = (Subject) o;
    return Objects.equals(defaultTitle, subject.defaultTitle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(defaultTitle);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Subject.class.getSimpleName() + "[", "]")
        .add("defaultTitle='" + defaultTitle + "'")
        .toString();
  }
}
