package by.arhor.university.database.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Consumer;

@XmlRootElement(name = "queries")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Queries implements Serializable {

  @XmlAttribute
  private String context;

  @XmlElements({
      @XmlElement(name = "drop",   type = DropQuery.class),
      @XmlElement(name = "create", type = CreateQuery.class),
      @XmlElement(name = "util",   type = UtilQuery.class)
  })
  private List<Query> queries;

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public List<Query> getQueries() {
    return queries;
  }

  public void setQueries(List<Query> queries) {
    this.queries = queries;
  }

  public void forEach(Consumer<Query> queryConsumer) {
    if (queries != null) {
      queries.forEach(queryConsumer);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Queries queries1 = (Queries) o;
    return Objects.equals(context, queries1.context)
        && Objects.equals(queries, queries1.queries);
  }

  @Override
  public int hashCode() {
    return Objects.hash(context, queries);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Queries.class.getSimpleName() + "[", "]")
        .add("context='" + context + "'")
        .add("queries=" + queries)
        .toString();
  }
}
