package by.arhor.university.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

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

  public CompositeId getId() {
    return id;
  }

  public void setId(CompositeId id) {
    this.id = id;
  }

  public short getScore() {
    return score;
  }

  public void setScore(short score) {
    this.score = score;
  }

  public Enrollee getEnrollee() {
    return enrollee;
  }

  public void setEnrollee(Enrollee enrollee) {
    this.enrollee = enrollee;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EnrolleeSubject that = (EnrolleeSubject) o;
    return score == that.score &&
        Objects.equals(id, that.id) &&
        Objects.equals(enrollee, that.enrollee) &&
        Objects.equals(subject, that.subject);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, score, enrollee, subject);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", EnrolleeSubject.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("score=" + score)
        .add("enrollee=" + enrollee)
        .add("subject=" + subject)
        .toString();
  }

  @Embeddable
  public static class CompositeId implements Serializable {
    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "lang_id", nullable = false)
    private Long langId;

    public CompositeId() {
    }

    public CompositeId(Long subjectId, Long langId) {
      this.subjectId = subjectId;
      this.langId = langId;
    }

    public Long getSubjectId() {
      return subjectId;
    }

    public void setSubjectId(Long subjectId) {
      this.subjectId = subjectId;
    }

    public Long getLangId() {
      return langId;
    }

    public void setLangId(Long langId) {
      this.langId = langId;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CompositeId that = (CompositeId) o;
      return Objects.equals(subjectId, that.subjectId) &&
          Objects.equals(langId, that.langId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(subjectId, langId);
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", CompositeId.class.getSimpleName() + "[", "]")
          .add("subjectId=" + subjectId)
          .add("langId=" + langId)
          .toString();
    }
  }
}
