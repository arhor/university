package by.arhor.university.database.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "query")
@XmlAccessorType(XmlAccessType.FIELD)
public final class Query implements Serializable {

  @XmlAttribute
  private String context;

  @XmlValue
  private String content;

  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Query query = (Query) o;
    return Objects.equals(context, query.context)
        && Objects.equals(content, query.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(context, content);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Query.class.getSimpleName() + "[", "]")
        .add("context='" + context + "'")
        .add("content='" + content + "'")
        .toString();
  }
}
