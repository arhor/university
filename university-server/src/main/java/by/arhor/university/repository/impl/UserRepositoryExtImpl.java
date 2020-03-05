package by.arhor.university.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import by.arhor.university.repository.UserRepositoryExt;
import org.springframework.stereotype.Repository;

import by.arhor.university.model.User;

@Repository
public class UserRepositoryExtImpl implements UserRepositoryExt {

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
