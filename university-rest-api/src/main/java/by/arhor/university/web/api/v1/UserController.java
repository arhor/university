package by.arhor.university.web.api.v1;

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
import org.springframework.web.context.request.WebRequest;

import by.arhor.university.service.UserService;
import by.arhor.university.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Lazy
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController extends ApiController {

  private final UserService service;

  @GetMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<?> getUser(@PathVariable Long id, WebRequest req) {
    return handle(service.findOne(id), req.getLocale());
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> register(@RequestBody UserDTO dto, WebRequest req) {
    return handle(service.create(dto), req.getLocale());
  }

  @PatchMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> update(@RequestBody UserDTO dto, WebRequest req) {
    return handle(service.update(dto), req.getLocale());
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteUser(Long id) {
    service.deleteById(id);
  }
}
