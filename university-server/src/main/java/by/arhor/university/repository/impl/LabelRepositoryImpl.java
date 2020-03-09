package by.arhor.university.repository.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.arhor.university.repository.LabelRepository;
import org.springframework.stereotype.Repository;

import by.arhor.university.model.Lang;

@Repository
public class LabelRepositoryImpl implements LabelRepository {

  private static final String SQL_GET_LOCALIZED_STRING = """
      SELECT lb.value
      FROM labels lb WITH(NOLOCK)
      WHERE lb.label = :label
      AND lb.lang_id = :lang_id
      """.stripLeading();

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<String> getLocalizedString(String label, Lang lang) {
    return Optional.ofNullable(
        entityManager
            .createNativeQuery(SQL_GET_LOCALIZED_STRING, String.class)
            .setParameter("label", label)
            .setParameter("lang_id", lang.getId())
            .getSingleResult()
            .toString()
    );
  }
}
