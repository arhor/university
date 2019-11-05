package by.bsu.uir.university.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "langs")
@EqualsAndHashCode(callSuper = true)
public class Lang extends AbstractEntity<Short> {

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "label", unique = true, nullable = false)
  private Lang.Value label;

  public enum Value {
    BY ("Belarusian"),
    RU ("Russian"),
    EN ("English");

    private final String name;

    Value(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}
