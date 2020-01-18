package by.arhor.university.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "roles")
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "getDefaultRole",
        procedureName = "getDefaultRole",
        resultClasses = Long.class

    )
})
public class Role extends AbstractModelObject<Short> {

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "title", unique = true, nullable = false)
  private Role.Value title;

  public Role() {}

  public Role(Role.Value title) {
    this.title = title;
  }

  public Value getTitle() {
    return title;
  }

  public void setTitle(Value title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Role role = (Role) o;
    return title == role.title;
  }

  @Override
  public int hashCode() {
    return Objects.hash(title);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Role.class.getSimpleName() + "[", "]")
        .add("title=" + title)
        .toString();
  }

  public enum Value {
    USER, ADMIN
  }
}
