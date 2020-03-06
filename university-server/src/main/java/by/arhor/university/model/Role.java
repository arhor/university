package by.arhor.university.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "roles")
@EqualsAndHashCode(callSuper = true)
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(
      name = "getDefaultRole",
      procedureName = "getDefaultRole",
      resultClasses = Long.class)
})
public class Role extends AbstractModelObject<Short> {

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "title", unique = true, nullable = false)
  private Role.Value title;

  public enum Value {
    USER,
    ADMIN
  }
}
