package by.arhor.university.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "langs")
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(name = "getDefaultLangId", procedureName = "getDefaultLang")
})
public class Lang implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Short id;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "label", unique = true, nullable = false)
  private Lang.Value label;

  public enum Value {
    RU("Russian"),
    EN("English");

    private final String name;

    Value(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}
