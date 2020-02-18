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
  private List<Dependency> dependencies;

  public List<Dependency> getDependencies() {
    return dependencies;
  }

  public void setDependencies(List<Dependency> dependencies) {
    this.dependencies = dependencies;
  }

  public void forEach(Consumer<Dependency> dependencyConsumer) {
    if (dependencies != null) {
      dependencies.forEach(dependencyConsumer);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Dependencies that = (Dependencies) o;
    return Objects.equals(dependencies, that.dependencies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dependencies);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Dependencies.class.getSimpleName() + "[", "]")
        .add("dependencies=" + dependencies)
        .toString();
  }
}
