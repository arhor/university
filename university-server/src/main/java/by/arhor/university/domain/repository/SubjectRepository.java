package by.arhor.university.domain.repository;

import by.arhor.university.domain.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
