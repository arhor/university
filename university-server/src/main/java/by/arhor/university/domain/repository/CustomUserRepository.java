package by.arhor.university.domain.repository;

import org.springframework.cache.annotation.CachePut;

import by.arhor.university.domain.model.User;

public interface CustomUserRepository {

  @CachePut(value = "cache_users", key = "#email")
  User createNewUser(String email, String password, String firstName, String lastName);

}
