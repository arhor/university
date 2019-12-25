package by.arhor.university.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import by.arhor.university.domain.model.Lang;

import java.util.Optional;

public interface LangRepository extends JpaRepository<Lang, Long> {

  @Procedure
  Lang getDefaultLang();

  @Query("SELECT l FROM langs l WHERE l.label = :label")
  Optional<Lang> findByLabel(String label);

}
