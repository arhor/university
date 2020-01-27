package by.arhor.university.domain.repository.impl;

import static by.arhor.university.domain.repository.impl.RepositoryUtils.columns;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import by.arhor.university.domain.model.Lang;
import by.arhor.university.domain.repository.LangRepository;

@Repository
public class LangRepositoryImpl implements LangRepository {

  private static final String COLUMNS = columns("id", "label");

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
  public List<Lang> findAll() {
    return entityManager
        .createQuery("SELECT l FROM Lang l ", Lang.class)
        .getResultList();
  }
}
