package by.arhor.university.service.dto;

import by.arhor.university.domain.model.Lang;
import by.arhor.university.domain.model.Role;

import java.util.Objects;
import java.util.StringJoiner;

public final class UserDTO implements DTO<Long> {

  private Long   id;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String role;
  private String lang;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public void setRole(Role role) {
    this.role = role.getTitle().name();
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  public void setLang(Lang lang) {
    this.lang = lang.getLabel().getName();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDTO userDto = (UserDTO) o;
    return Objects.equals(id, userDto.id)
        && Objects.equals(email, userDto.email)
        && Objects.equals(password, userDto.password)
        && Objects.equals(firstName, userDto.firstName)
        && Objects.equals(lastName, userDto.lastName)
        && Objects.equals(role, userDto.role)
        && Objects.equals(lang, userDto.lang);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, password, firstName, lastName, role, lang);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", UserDTO.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("email='" + email + "'")
        .add("password='" + password + "'")
        .add("firstName='" + firstName + "'")
        .add("lastName='" + lastName + "'")
        .add("role='" + role + "'")
        .add("lang='" + lang + "'")
        .toString();
  }
}
