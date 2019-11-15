package by.bsu.uir.university.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
@Entity
@Table(name = "enrollees_has_subjects")
public class EnrolleeSubject {

  @EmbeddedId
  private CompositeId id;

  @Min(0)
  @Max(100)
  @Column(name = "score", nullable = false)
  private short score;

  @ManyToOne(optional = false)
  @MapsId("enrollee_id")
  private Enrollee enrollee;

  @ManyToOne(optional = false)
  @MapsId("subject_id")
  private Subject subject;

  @Data
  @Embeddable
  public static class CompositeId implements Serializable {
    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "lang_id", nullable = false)
    private Long langId;
  }
}
