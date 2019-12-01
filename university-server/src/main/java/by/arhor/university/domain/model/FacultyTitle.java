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
@Table(name = "faculty_titles")
public class FacultyTitle {

  @EmbeddedId
  private CompositeId id;

  @NotEmpty
  @Column(name = "title", nullable = false, length = 64)
  private String title;

  @ManyToOne(optional = false)
  @MapsId("faculty_id")
  private Faculty faculty;

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

  public Faculty getFaculty() {
    return faculty;
  }

  public void setFaculty(Faculty faculty) {
    this.faculty = faculty;
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
    FacultyTitle that = (FacultyTitle) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(title, that.title) &&
        Objects.equals(faculty, that.faculty) &&
        Objects.equals(lang, that.lang);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, faculty, lang);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", FacultyTitle.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("faculty=" + faculty)
        .add("lang=" + lang)
        .toString();
  }

  @Embeddable
  public static class CompositeId implements Serializable {
    @Column(name = "faculty_id", nullable = false)
    private Long facultyId;

    @Column(name = "lang_id", nullable = false)
    private Short langId;

    public CompositeId() {
    }

    public CompositeId(Long facultyId, Short langId) {
      this.facultyId = facultyId;
      this.langId = langId;
    }

    public Long getFacultyId() {
      return facultyId;
    }

    public void setFacultyId(Long facultyId) {
      this.facultyId = facultyId;
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
      return Objects.equals(facultyId, that.facultyId) &&
          Objects.equals(langId, that.langId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(facultyId, langId);
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", CompositeId.class.getSimpleName() + "[", "]")
          .add("facultyId=" + facultyId)
          .add("langId=" + langId)
          .toString();
    }
  }
}
