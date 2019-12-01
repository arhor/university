package by.arhor.university.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

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

  public CompositeId getId() {
    return id;
  }

  public void setId(CompositeId id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public Lang getLang() {
    return lang;
  }

  public void setLang(Lang lang) {
    this.lang = lang;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SubjectTitle that = (SubjectTitle) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(title, that.title) &&
        Objects.equals(subject, that.subject) &&
        Objects.equals(lang, that.lang);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, subject, lang);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SubjectTitle.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("subject=" + subject)
        .add("lang=" + lang)
        .toString();
  }

  @Embeddable
  public static class CompositeId implements Serializable {
    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "lang_id", nullable = false)
    private Short langId;

    public Long getSubjectId() {
      return subjectId;
    }

    public void setSubjectId(Long subjectId) {
      this.subjectId = subjectId;
    }

    public Short getLangId() {
      return langId;
    }

    public void setLangId(Short langId) {
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
