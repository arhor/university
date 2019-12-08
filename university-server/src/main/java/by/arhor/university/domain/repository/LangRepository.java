package by.arhor.university.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import by.arhor.university.domain.model.Lang;

public interface LangRepository extends JpaRepository<Lang, Long> {

  @Procedure
  Lang getDefaultLang();

}
