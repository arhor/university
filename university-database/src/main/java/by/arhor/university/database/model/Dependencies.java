package by.arhor.university.database.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Consumer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dependencies")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Dependencies implements Serializable {

  @XmlElement(name = "dependency")
  private List<Dependency> list;

  public List<Dependency> getList() {
    return list;
  }

  public void setList(List<Dependency> list) {
    this.list = list;
  }

  public void forEach(Consumer<Dependency> dependencyConsumer) {
    if (list != null) {
      list.forEach(dependencyConsumer);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Dependencies that = (Dependencies) o;
    return Objects.equals(list, that.list);
  }

  @Override
  public int hashCode() {
    return Objects.hash(list);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Dependencies.class.getSimpleName() + "[", "]")
        .add("dependencies=" + list)
        .toString();
  }
}
