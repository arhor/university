package by.arhor.university.web.api.v1;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.domain.model.Role;
import by.arhor.university.domain.repository.RoleRepository;

@Lazy
@RestController
@RequestMapping(path = "/api/v1/roles")
public class RoleController {

  private final RoleRepository repository;

  @Autowired
  public RoleController(RoleRepository repository) {
    this.repository = repository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Role> getRoles() {
//    return repository.findAll();
    return Arrays.stream(Role.Value.values()).map(Role::new).collect(toList()); // FIXME: stub!
  }

  @GetMapping(path = "/default", produces = MediaType.APPLICATION_JSON_VALUE)
  public Role getDefaultRole() {
    return repository.getDefaultRole();
  }

}
