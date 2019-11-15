package by.bsu.uir.university.domain.model;

import java.util.List;

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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "enrollees")
@EqualsAndHashCode(callSuper = true, exclude = {"user"})
@ToString(exclude = {"user"})
public class Enrollee extends AbstractEntity<Long> {

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

}
