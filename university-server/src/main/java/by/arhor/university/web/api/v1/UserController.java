package by.arhor.university.web.api.v1;

import static by.arhor.university.web.api.v1.Root.API_V_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.service.UserService;
import by.arhor.university.service.dto.UserDTO;

@Lazy
@RestController
@RequestMapping(path = API_V_1 + "/users")
public class UserController {

  private final UserService service;

  @Autowired
  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping(
      path = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDTO getUser(@PathVariable Long id) {
    return service.findOne(id);
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO register(@RequestBody UserDTO dto) {
    return service.create(dto);
  }

  @PatchMapping(
      path = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDTO update(@RequestBody UserDTO dto) {
    return service.update(dto);
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteUser(Long id) {
    service.deleteById(id);
  }

}
