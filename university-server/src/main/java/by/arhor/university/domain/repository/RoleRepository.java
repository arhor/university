package by.arhor.university.domain.repository;

import java.util.Optional;

import by.arhor.university.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

public interface RoleRepository extends JpaRepository<Role, Long> {

  @Procedure
  Role getDefaultRole();

  @Query("SELECT r from Role r WHERE r.title = :title")
  Optional<Role> findRoleByTitle(Role.Value title);

}
