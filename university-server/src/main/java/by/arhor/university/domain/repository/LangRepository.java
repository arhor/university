package by.arhor.university.domain.repository;

import by.arhor.university.domain.model.Lang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface LangRepository extends JpaRepository<Lang, Long> {

  @Procedure
  Lang getDefaultLang();

}
