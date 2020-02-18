package by.arhor.university.database.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "module")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Module implements Serializable {

  @XmlAttribute
  private String name;

  @XmlElement(name = "dependencies")
  private Dependencies dependencies;

  @XmlElement(name = "queries")
  private Queries queries;

  @XmlTransient
  private boolean resolved;

  @XmlTransient
  private int executionCost;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Dependencies getDependencies() {
    return dependencies;
  }

  public void setDependencies(Dependencies dependencies) {
    this.dependencies = dependencies;
  }

  public Queries getQueries() {
    return queries;
  }

  public void setQueries(Queries queries) {
    this.queries = queries;
  }

  public boolean isResolved() {
    return resolved;
  }

  public void setResolved(boolean resolved) {
    this.resolved = resolved;
  }

  public int getExecutionCost() {
    return executionCost;
  }

  public void setExecutionCost(int executionCost) {
    this.executionCost = executionCost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Module module = (Module) o;
    return Objects.equals(name, module.name)
        && Objects.equals(dependencies, module.dependencies)
        && Objects.equals(queries, module.queries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, dependencies, queries);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Module.class.getSimpleName() + "[", "]")
        .add("name='" + name + "'")
        .add("dependencies=" + dependencies)
        .add("queries=" + queries)
        .toString();
  }
}
