package by.arhor.university.web.api.v1;

import by.arhor.university.service.FacultyService;
import by.arhor.university.service.dto.FacultyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static by.arhor.university.web.api.util.PageUtils.paginate;

@Lazy
@RestController
@RequestMapping(path = "/api/v1/faculties")
public class FacultyController {

  private final FacultyService service;

  @Autowired
  public FacultyController(FacultyService service) {
    this.service = service;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<FacultyDTO> getFaculties(
      @RequestParam Integer page,
      @RequestParam Integer size) {
    return paginate(service::findPage).apply(page, size);
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public FacultyDTO getFaculty(@PathVariable("id") Long id) {
    return service.findOne(id);
  }

  @DeleteMapping(path = "/{id}")
  public void deleteFaculty(@PathVariable("id") Long id) {
    service.deleteById(id);
  }

}
