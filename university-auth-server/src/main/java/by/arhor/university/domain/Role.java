package by.arhor.university.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "roles")
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(name = "getDefaultRoleId", procedureName = "getDefaultRole")
})
public class Role implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "title", unique = true, nullable = false)
  private Role.Value title;

  public enum Value {
    USER,
    ADMIN
  }
}
