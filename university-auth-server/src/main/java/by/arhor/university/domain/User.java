package by.arhor.university.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(
      name = "createNewUser",
      procedureName = "createNewUser",
      resultClasses = User.class,
      parameters = {
        @StoredProcedureParameter(name = "email", type = String.class),
        @StoredProcedureParameter(name = "password", type = String.class),
        @StoredProcedureParameter(name = "first_name", type = String.class),
        @StoredProcedureParameter(name = "last_name", type = String.class),
        @StoredProcedureParameter(name = "role_id", type = Long.class),
        @StoredProcedureParameter(name = "lang_id", type = Long.class),
      })
})
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @NotEmpty
  @Size(min = 6, max = 255)
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @NotEmpty
  @Column(name = "password", nullable = false, length = 512)
  private String password;

  @NotEmpty
  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @NotEmpty
  @Column(name = "last_name", nullable = false, length = 50)
  private String lastName;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
  private Role role;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "lang_id", nullable = false, referencedColumnName = "id")
  private Lang lang;
}
