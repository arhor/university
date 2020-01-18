package by.arhor.university.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import by.arhor.university.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  @Procedure("createNewUser")
  User createNewUser(User user);

  @Query(
      "SELECT u " +
      "FROM User u " +
      "WHERE u.email = :email")
  Optional<User> findByEmail(@Param("email") String email);

  @Query(
      "SELECT COUNT(u) " +
      "FROM User u " +
      "WHERE u.email = :email")
  long countByEmail(@Param("email") String email);

}
