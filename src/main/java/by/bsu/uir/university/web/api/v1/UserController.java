package by.bsu.uir.university.web.api.v1;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.bsu.uir.university.service.UserService;
import by.bsu.uir.university.service.dto.UserDto;
import lombok.RequiredArgsConstructor;

@Lazy
@RestController
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void register(@RequestBody UserDto userDto) {
    // FIXME
  }

  @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public void login(@RequestBody UserDto userDto) {
    // FIXME
  }

}
