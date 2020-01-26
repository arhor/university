package by.arhor.university.domain.repository.impl;

import by.arhor.university.domain.model.Lang;
import by.arhor.university.domain.repository.LangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static by.arhor.university.domain.repository.impl.RepositoryUtils.columns;

@Repository
public class LangRepositoryImpl implements LangRepository {

  private static final String COLUMNS = columns("id", "label");

  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Lang> rowMapper;

  @Autowired
  public LangRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.rowMapper = (rs, numRow) -> {
      var lang = new Lang(Lang.Value.valueOf(rs.getString("label")));
      lang.setId(rs.getShort("id"));
      return lang;
    };
  }

  @Override
  public Optional<Lang> findByLabel(String label) {
    return Optional.ofNullable(
        jdbcTemplate.queryForObject(
            "SELECT" + COLUMNS + "FROM langs  WITH(NOLOCK) WHERE [label] = :label",
            new Object[] { label },
            Lang.class
        )
    );
  }

  @Override
  public List<Lang> findAll() {
    return jdbcTemplate.query(
        "SELECT" + COLUMNS + "FROM langs WITH(NOLOCK)",
        rowMapper
    );
  }
}
