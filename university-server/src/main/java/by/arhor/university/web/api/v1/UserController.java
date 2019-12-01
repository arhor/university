package by.arhor.university.web.api.v1;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.service.UserService;
import by.arhor.university.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;

@Lazy
@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void register(@RequestBody UserDTO userDto) {
    // FIXME
  }

  @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void login(@RequestBody UserDTO userDto) {
    // FIXME
  }

}
