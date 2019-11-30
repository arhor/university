package by.bsu.uir.university.domain.repository;

import by.bsu.uir.university.domain.model.Enrollee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {
}
