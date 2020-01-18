package by.arhor.university.web.api.model;

import java.util.Objects;
import java.util.StringJoiner;

public final class SignInRequest {

  private String email;
  private String password;

  public SignInRequest() {}

  public SignInRequest(String email, String password) {
    this.email = email;
    this.password = password;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SignInRequest that = (SignInRequest) o;
    return Objects.equals(email, that.email) &&
        Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, password);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SignInRequest.class.getSimpleName() + "[", "]")
        .add("email='" + email + "'")
        .add("password='" + password + "'")
        .toString();
  }
}
