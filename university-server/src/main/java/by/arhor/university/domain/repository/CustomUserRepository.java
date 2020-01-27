package by.arhor.university.domain.repository;

import by.arhor.university.domain.model.User;

public interface CustomUserRepository {

  User createNewUser(String email, String password, String firstName, String lastName);

}
