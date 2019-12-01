package by.arhor.university.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

  @GetMapping(path = "/")
  public String getHelloFromEndpoint() {
    return "Hello yoba!";
  }

}
