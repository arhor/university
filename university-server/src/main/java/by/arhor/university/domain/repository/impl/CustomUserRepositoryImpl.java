package by.arhor.university.domain.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Repository;

import by.arhor.university.domain.model.User;
import by.arhor.university.domain.repository.CustomUserRepository;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public User createNewUser(String email, String password, String firstName, String lastName) {
    return (User) entityManager
        .createNamedStoredProcedureQuery("createNewUser")
        .setParameter("email", email)
        .setParameter("password", password)
        .setParameter("first_name", firstName)
        .setParameter("last_name", lastName)
        .setParameter("role_id", null)
        .setParameter("lang_id", null)
        .getSingleResult();
  }

}
