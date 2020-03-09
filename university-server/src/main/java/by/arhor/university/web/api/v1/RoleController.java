package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.CACHE_ROLES;
import static by.arhor.university.Constants.REST_API_V_1;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.model.Role;
import by.arhor.university.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Lazy
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = REST_API_V_1 + "/roles")
public class RoleController extends ApiController {

  private final RoleRepository repository;

  @Cacheable(cacheNames = CACHE_ROLES)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Role> getRoles() {
    return repository.findAll();
  }

  @GetMapping(path = "/default", produces = MediaType.APPLICATION_JSON_VALUE)
  public Role.Value getDefaultRole() {
    return repository.getDefaultRole().map(Role::getTitle).orElse(Role.Value.USER);
  }
}
