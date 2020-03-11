package by.arhor.university.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.domain.User;
import by.arhor.university.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @GetMapping(produces = "application/json")
  public List<User> getAllUsers() {
    return service.findAll();
  }

  @GetMapping(path = "/current", produces = "application/json")
  public Principal getUser(Principal principal) {
    return principal;
  }

  @PreAuthorize("#oauth2.hasScope('server')")
  @PostMapping(consumes = "application/json")
  public void createUser(@Valid @RequestBody User user) {
    service.create(user);
  }
}
