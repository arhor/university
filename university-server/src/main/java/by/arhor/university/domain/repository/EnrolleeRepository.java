package by.arhor.university.domain.repository;

import by.arhor.university.domain.model.Enrollee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {
}
