package by.arhor.university.database.model;

import java.util.Objects;
import java.util.StringJoiner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "drop-query")
@XmlAccessorType(XmlAccessType.FIELD)
public final class DropQuery extends Query {

  @XmlAttribute
  private String target;

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    DropQuery dropQuery = (DropQuery) o;
    return Objects.equals(target, dropQuery.target);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), target);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", DropQuery.class.getSimpleName() + "[", "]")
        .add("target='" + target + "'")
        .toString();
  }
}
