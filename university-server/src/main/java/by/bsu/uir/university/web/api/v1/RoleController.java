package by.bsu.uir.university.web.api.v1;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.bsu.uir.university.domain.model.Role;
import by.bsu.uir.university.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;

@Lazy
@RestController
@RequestMapping(path = "/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleRepository repository;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Role> getRoles() {
    return repository.findAll();
  }

}
