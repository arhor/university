package by.bsu.uir.university.domain.repository;

import by.bsu.uir.university.domain.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
