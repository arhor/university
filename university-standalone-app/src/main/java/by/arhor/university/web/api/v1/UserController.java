package by.arhor.university.web.api.v1;

import by.arhor.university.service.UserService;
import by.arhor.university.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

import static by.arhor.university.Constants.REST_API_V_1;

@Lazy
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(
    path = REST_API_V_1 + "/users",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends ApiController {

  private final UserService service;

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable Long id, Locale locale) {
    return handle(service.findOne(id), locale);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> register(@RequestBody UserDTO dto, Locale locale) {
    return handle(service.create(dto), locale);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> update(@RequestBody UserDTO dto, Locale locale) {
    return handle(service.update(dto), locale);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteUser(Long id) {
    service.deleteById(id);
  }
}
