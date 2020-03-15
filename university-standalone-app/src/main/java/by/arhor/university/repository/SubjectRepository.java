package by.arhor.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.arhor.university.model.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

  @Query(
      nativeQuery = true,
      value = "SELECT s.id, s.default_title " +
              "FROM subjects s " +
              "JOIN enrollees_has_subjects ehs ehs.subject_id = s.id " +
              "JOIN enrollees e ON e.id = ehs.enrollee_id " +
              "JOIN users u ON e.user_id = u.id " +
              "WHERE u.email = :email")
  List<Subject> findSubjectsByUserEmail(String email);
}
