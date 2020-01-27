package by.arhor.university.web.api.model;

import java.util.Objects;
import java.util.StringJoiner;

public final class SignUpRequest {

  private String email;
  private String password;
  private String firstName;
  private String lastName;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SignUpRequest that = (SignUpRequest) o;
    return Objects.equals(email, that.email) &&
        Objects.equals(password, that.password) &&
        Objects.equals(firstName, that.firstName) &&
        Objects.equals(lastName, that.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password, firstName, lastName);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SignUpRequest.class.getSimpleName() + "[", "]")
        .add("email='" + email + "'")
        .add("password='" + password + "'")
        .add("firstName='" + firstName + "'")
        .add("lastName='" + lastName + "'")
        .toString();
  }
}
