package by.arhor.university.domain.repository.impl;

import by.arhor.university.domain.model.Lang;
import by.arhor.university.domain.repository.LangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@Repository
public class LangRepositoryImpl implements LangRepository {

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
            "SELECT l FROM langs l WHERE l.label = :label",
            new Object[] { label },
            Lang.class
        )
    );
  }

  @Override
  public List<Lang> findAll() {

    return jdbcTemplate.query(
        "SELECT " +
            getColumns().stream()
                .map(name -> '[' + name + ']')
                .map(name -> "l." + name)
                .collect(joining()) +
            " FROM langs l WITH(NOLOCK)",
        rowMapper
    );
  }

  private List<String> getColumns() {
    return List.of("id", "label");
  }

}
