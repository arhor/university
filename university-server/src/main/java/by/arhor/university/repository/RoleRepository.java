package by.arhor.university.repository;

import java.util.Optional;

import by.arhor.university.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

public interface RoleRepository extends JpaRepository<Role, Long> {

  @Procedure("getDefaultRole")
  Long getDefaultRole();

  @Query("SELECT r from Role r WHERE r.title = :title")
  Optional<Role> findRoleByTitle(Role.Value title);

}
