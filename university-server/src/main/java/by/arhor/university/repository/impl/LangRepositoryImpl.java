package by.arhor.university.repository.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.arhor.university.repository.LangRepository;
import org.springframework.stereotype.Repository;

import by.arhor.university.model.Lang;

@Repository
public class LangRepositoryImpl implements LangRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<Lang> findByLabel(String label) {
    return Optional.ofNullable(
        entityManager
            .createQuery("SELECT l FROM Lang WHERE l.label = :label", Lang.class)
            .setParameter("label", label)
            .getSingleResult()
    );
  }

  @Override
  public short getDefaultLangId() {
    var langId = (Number) entityManager.createNamedStoredProcedureQuery("getDefaultLangId").getSingleResult();
    return langId != null ? langId.shortValue() : -1;
  }

  @Override
  public List<Lang> findAll() {
    return entityManager
        .createQuery("SELECT l FROM Lang l ", Lang.class)
        .getResultList();
  }
}
