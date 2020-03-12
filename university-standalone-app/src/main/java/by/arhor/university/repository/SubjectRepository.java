package by.arhor.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.arhor.university.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
