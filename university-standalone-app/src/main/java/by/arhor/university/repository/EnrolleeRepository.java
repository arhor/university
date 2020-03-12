package by.arhor.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.arhor.university.model.Enrollee;

public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {
}
