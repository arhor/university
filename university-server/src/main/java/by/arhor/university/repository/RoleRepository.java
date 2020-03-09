package by.arhor.university.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import by.arhor.university.model.Role;

public interface RoleRepository {

  @Query("SELECT r from Role r WHERE r.title = :title")
  Optional<Role> findRoleByTitle(String title);

  Optional<Role> getDefaultRole();

  List<Role> findAll();

}
