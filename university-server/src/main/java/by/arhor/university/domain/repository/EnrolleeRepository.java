package by.arhor.university.domain.repository;

import by.arhor.university.domain.model.Enrollee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {
}
