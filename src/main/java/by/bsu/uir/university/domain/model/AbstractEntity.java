package by.bsu.uir.university.domain.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class AbstractEntity<K> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected K id;

}
