package by.arhor.university.domain.repository.impl;

import by.arhor.university.domain.model.Lang;
import by.arhor.university.domain.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LabelRepositoryImpl implements LabelRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public LabelRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<String> getLocalizedString(String label, Lang lang) {
    return Optional.ofNullable(
        jdbcTemplate.queryForObject(
            "SELECT lb.value FROM labels lb WITH(NOLOCK) WHERE lb.label = ? AND lb.lang_id = ?",
            new Object[] { label, lang.getId() },
            String.class
        )
    );
  }

}
