package by.bsu.uir.university.domain.repository;

import by.bsu.uir.university.domain.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
