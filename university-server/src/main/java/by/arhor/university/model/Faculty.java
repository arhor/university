package by.arhor.university.model;

import java.util.List;

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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;

@Data
@Entity
@Table(name = "faculties")
@EqualsAndHashCode(callSuper = true, exclude = {"subjects"})
@ToString(exclude = {"subjects"})
public class Faculty extends AbstractModelObject<Long> {

  @NotEmpty
  @Column(name = "default_title", nullable = false, unique = true, length = 64)
  private String defaultTitle;

  @Min(0)
  @Column(name = "seats_paid", nullable = false)
  private Short seatsPaid;

  @Min(0)
  @Column(name = "seats_budget", nullable = false)
  private Short seatsBudget;

  @OneToMany(
      mappedBy = "faculty",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  @BatchSize(size = 50)
  private List<FacultyEnrollee> facultyEnrollees;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "faculties_has_subjects",
      joinColumns = @JoinColumn(name = "faculty_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "subject_id", nullable = false))
  @BatchSize(size = 10)
  private List<Subject> subjects;
}
