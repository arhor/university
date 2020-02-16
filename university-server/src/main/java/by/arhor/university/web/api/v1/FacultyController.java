package by.arhor.university.web.api.v1;

import static by.arhor.university.web.api.util.PageUtils.paginate;
import static by.arhor.university.web.api.v1.ApiController.API_V_1;

import java.util.List;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

@Lazy
@RestController
@RequestMapping(path = API_V_1 + "/faculties")
public class FacultyController extends ApiController {

  private final FacultyService service;
  private final BiFunction<Integer, Integer, List<FacultyDTO>> findPage;

  @Autowired
  public FacultyController(FacultyService service) {
    this.service = service;
    this.findPage = paginate(service::findPage);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<FacultyDTO> getFaculties(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size,
      WebRequest request) {
    return findPage.apply(page, size);
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getFaculty(@PathVariable("id") Long id, WebRequest req) {
    return handle(service.findOne(id), req.getLocale());
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void deleteFaculty(
      @PathVariable("id") Long id,
      WebRequest request) {
    service.deleteById(id);
  }
}
