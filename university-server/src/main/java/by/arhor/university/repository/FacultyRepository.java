package by.arhor.university.repository;

import by.arhor.university.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

  boolean existsByDefaultTitle(String defaultTitle);

}
