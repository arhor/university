package by.arhor.university.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import by.arhor.university.domain.User;

public interface UserService extends UserDetailsService {

  void create(User user);

  List<User> findAll();

}
