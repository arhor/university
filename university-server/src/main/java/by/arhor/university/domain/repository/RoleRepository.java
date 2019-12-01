package by.arhor.university.domain.repository;

import java.util.Optional;

import by.arhor.university.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query("SELECT r from Role r WHERE r.title = :title")
  Optional<Role> findRoleByTitle(Role.Value title);

}
