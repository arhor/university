package by.arhor.university.database.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dependency")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Dependency implements Serializable {

  @XmlAttribute
  private String name;

  public Dependency() {}

  public Dependency(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Dependency that = (Dependency) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Dependency.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .toString();
  }
}
