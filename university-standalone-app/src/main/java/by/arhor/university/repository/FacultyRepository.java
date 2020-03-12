package by.arhor.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.arhor.university.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

  boolean existsByDefaultTitle(String defaultTitle);

}
