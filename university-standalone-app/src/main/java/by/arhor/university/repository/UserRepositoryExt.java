package by.arhor.university.repository;

import static by.arhor.university.Constants.CACHE_USERS;

import org.springframework.cache.annotation.CachePut;

import by.arhor.university.model.User;

public interface UserRepositoryExt {

  @CachePut(value = CACHE_USERS, key = "#email")
  User createNewUser(String email, String password, String firstName, String lastName);

}
