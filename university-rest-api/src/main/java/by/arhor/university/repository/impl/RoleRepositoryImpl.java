package by.arhor.university.repository.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import by.arhor.university.model.Lang;
import by.arhor.university.model.Role;
import by.arhor.university.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class RoleRepositoryImpl implements RoleRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<Role> findRoleByTitle(String title) {
    return Optional.ofNullable(
        entityManager
            .createQuery("SELECT r FROM Role r WHERE r.title=:title", Role.class)
            .setParameter("title", title)
            .getSingleResult()
    );
  }

  @Override
  public Optional<Role> getDefaultRole() {
    var roleId = (Number) entityManager.createNamedStoredProcedureQuery("getDefaultRoleId").getSingleResult();
    log.debug("default role id: {}", roleId);
    return Optional.ofNullable(
        entityManager
            .createQuery("SELECT r FROM Role r WHERE r.id=:id", Role.class)
            .setParameter("id",  roleId.shortValue())
            .getSingleResult()
    );
  }

  @Override
  public List<Role> findAll() {
    return entityManager
        .createQuery("SELECT r FROM Role r", Role.class)
        .getResultList();
  }
}
