package by.arhor.university.domain.repository;

import static by.arhor.university.Constants.CACHE_USERS;

import org.springframework.cache.annotation.CachePut;

import by.arhor.university.domain.model.User;

public interface CustomUserRepository {

  @CachePut(value = CACHE_USERS, key = "#email")
  User createNewUser(String email, String password, String firstName, String lastName);

}
