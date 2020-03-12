package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.REST_API_V_1;
import static by.arhor.university.web.api.util.PageUtils.bound;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import by.arhor.university.service.FacultyService;
import by.arhor.university.service.dto.FacultyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Lazy
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = REST_API_V_1 + "/faculties")
public class FacultyController extends ApiController {

  private final FacultyService service;

  @GetMapping(produces = "application/json")
  public List<FacultyDTO> getFaculties(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size,
      WebRequest request) {
    return bound(service::findPage).apply(page, size);
  }

  @GetMapping(path = "/{id}", produces = "application/json")
  public ResponseEntity<?> getFaculty(@PathVariable("id") Long id, WebRequest req) {
    return handle(service.findOne(id), req.getLocale());
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteFaculty(@PathVariable("id") Long id, WebRequest req) {
    service.deleteById(id);
  }
}
