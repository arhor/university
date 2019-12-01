package by.arhor.university.web.controller;

import by.arhor.university.service.FacultyService;
import by.arhor.university.service.dto.FacultyDto;
import by.arhor.university.web.controller.util.Constants;
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

@Lazy
@RestController
@RequestMapping(path = {"/api/faculties"})
public class FacultyController {

  private final FacultyService service;

  @Autowired
  public FacultyController(FacultyService service) {
    this.service = service;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<FacultyDto> getAllFaculties() {
    return service.findAll();
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<FacultyDto> getFacultiesPage(
      @RequestParam Integer page,
      @RequestParam Integer size) {
    return service.findPage(
        (page != null) ? page : Constants.DEFAULT_PAGE,
        (size != null) ? size : Constants.DEFAULT_SIZE);
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public FacultyDto getFaculty(@PathVariable("id") Long id) {
    return service.findOne(id);
  }

  @DeleteMapping(path = "/{id}")
  public void deleteFaculty(@PathVariable("id") Long id) {
    service.deleteById(id);
  }

}
