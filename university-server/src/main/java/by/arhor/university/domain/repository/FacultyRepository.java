package by.arhor.university.domain.repository;

import by.arhor.university.domain.model.Faculty;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

  boolean existsByDefaultTitle(String defaultTitle);

}
