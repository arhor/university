package by.arhor.university.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.StringJoiner;

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
            @StoredProcedureParameter(name = "lang_id", type = Long.class)
        }
    )
})
public class User extends AbstractModelObject<Long> {

  @NotEmpty
  @Size(min = 6, max = 255)
  @Column(name = "email", unique = true, nullable = false, length = 255)
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

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")
  private Role role;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "lang_id", nullable = false, referencedColumnName = "id")
  private Lang lang;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
  private Enrollee enrollee;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Lang getLang() {
    return lang;
  }

  public void setLang(Lang lang) {
    this.lang = lang;
  }

  public Enrollee getEnrollee() {
    return enrollee;
  }

  public void setEnrollee(Enrollee enrollee) {
    this.enrollee = enrollee;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(email, user.email) &&
        Objects.equals(password, user.password) &&
        Objects.equals(firstName, user.firstName) &&
        Objects.equals(lastName, user.lastName) &&
        Objects.equals(role, user.role) &&
        Objects.equals(lang, user.lang);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password, firstName, lastName, role, lang);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("email='" + email + "'")
        .add("password='" + password + "'")
        .add("firstName='" + firstName + "'")
        .add("lastName='" + lastName + "'")
        .add("role=" + role)
        .add("lang=" + lang)
        .toString();
  }
}
