package by.arhor.university.repository.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import by.arhor.university.model.Lang;
import by.arhor.university.repository.LangRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class LangRepositoryImpl implements LangRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<Lang> findLangByLabel(String label) {
    return Optional.ofNullable(
        entityManager
            .createQuery("SELECT l FROM Lang l WHERE l.label=:label", Lang.class)
            .setParameter("label", label)
            .getSingleResult()
    );
  }

  @Override
  public Optional<Lang> getDefaultLang() {
    var langId = (Number) entityManager.createNamedStoredProcedureQuery("getDefaultLangId").getSingleResult();
    log.debug("default language id: {}", langId);
    return Optional.ofNullable(
        entityManager
            .createQuery("SELECT l FROM Lang l WHERE l.id=:id", Lang.class)
            .setParameter("id",  langId.shortValue())
            .getSingleResult()
    );
  }

  @Override
  public List<Lang> findAll() {
    return entityManager
        .createQuery("SELECT l FROM Lang l", Lang.class)
        .getResultList();
  }
}
