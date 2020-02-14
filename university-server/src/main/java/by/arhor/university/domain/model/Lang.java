package by.arhor.university.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "langs")
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "getDefaultLangId",
        procedureName = "getDefaultLang"
    )
})
public class Lang extends AbstractModelObject<Short> {

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "label", unique = true, nullable = false)
  private Lang.Value label;

  public Lang() {}

  public Lang(Lang.Value label) {
    this.label = label;
  }

  public Value getLabel() {
    return label;
  }

  public void setLabel(Value label) {
    this.label = label;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Lang lang = (Lang) o;
    return label == lang.label;
  }

  @Override
  public int hashCode() {
    return Objects.hash(label);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Lang.class.getSimpleName() + "[", "]")
        .add("label=" + label)
        .toString();
  }

  public enum Value {
    RU ("Russian"),
    EN ("English");

    private final String name;

    Value(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}
