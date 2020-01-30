package by.arhor.university.domain.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import by.arhor.university.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

  @Query("SELECT u FROM User u WHERE u.email = :email")
  @Cacheable("user_details")
  Optional<User> findByEmail(@Param("email") String email);

  @Query("SELECT COUNT(u) FROM User u WHERE u.email = :email")
  long countByEmail(@Param("email") String email);

}
