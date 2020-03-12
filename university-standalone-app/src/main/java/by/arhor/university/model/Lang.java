package by.arhor.university.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "langs")
@EqualsAndHashCode(callSuper = true)
@NamedStoredProcedureQueries({
  @NamedStoredProcedureQuery(name = "getDefaultLangId", procedureName = "getDefaultLang")
})
public class Lang extends AbstractModelObject<Short> {

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
