package by.arhor.university.core.pattern.observer;

import java.util.Objects;
import java.util.StringJoiner;

import org.junit.jupiter.api.Test;

public class ObserverTest {

  static class User {

    String username;

    String password;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
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
      User user = (User) o;
      return Objects.equals(username, user.username) &&
          Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
      return Objects.hash(username, password);
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
          .add("username='" + username + "'")
          .add("password='" + password + "'")
          .toString();
    }
  }

  @Test
  public void test_number() {
    var observable1 = Observable.of("1");
    var observable2 = Observable.of(2);

    System.out.println(observable1.getClass());
    System.out.println(observable2.getClass());
  }

  @Test
  public void test_object() {
    var observable = Observable.of(new User());

    observable.subscribe(user -> System.out.println("observer noticed: " + user));

    observable.mutate(User::setUsername, "Max");
    observable.mutate(User::setPassword, "secret");

    var setter = observable.buildSetter(User::setUsername);
    var getter = observable.buildGetter(User::getUsername);

    setter.accept("Rishi");

    System.out.println(observable.access(User::getUsername));
    System.out.println(getter.get());
  }
}
