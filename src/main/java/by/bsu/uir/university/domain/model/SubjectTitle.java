package by.bsu.uir.university.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "subject_titles")
public class SubjectTitle {

  @EmbeddedId
  private CompositeId id;

  @NotEmpty
  @Column(name = "title", nullable = false, length = 64)
  private String title;

  @ManyToOne(optional = false)
  @MapsId("subject_id")
  private Subject subject;

  @ManyToOne(optional = false)
  @MapsId("lang_id")
  private Lang lang;

  @Data
  @Embeddable
  public static class CompositeId implements Serializable {
    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "lang_id", nullable = false)
    private Short langId;
  }
}
