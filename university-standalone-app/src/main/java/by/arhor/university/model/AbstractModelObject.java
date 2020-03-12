package by.arhor.university.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractModelObject<ID extends Serializable> implements ModelObject<ID> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected ID id;

  @Override
  public ID getId() {
    return id;
  }

  @Override
  public void setId(ID id) {
    this.id = id;
  }
}
