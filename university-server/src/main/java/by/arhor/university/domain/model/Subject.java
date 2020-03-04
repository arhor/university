package by.arhor.university.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "subjects")
@EqualsAndHashCode(callSuper = true, exclude = {"faculties"})
@ToString(exclude = {"faculties"})
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
}
