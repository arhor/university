package by.arhor.university.web.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.core.IntBiFunction;
import by.arhor.university.service.EnrolleeService;
import by.arhor.university.service.dto.EnrolleeDTO;
import by.arhor.university.web.api.util.PageUtils;

@RestController
@RequestMapping(
    path = "/api/v1/enrollees",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class EnrolleeController {

  private final EnrolleeService service;
  private final IntBiFunction<List<EnrolleeDTO>> pageRequest;

  @Autowired
  public EnrolleeController(EnrolleeService service) {
    this.service = service;
    this.pageRequest = PageUtils.paginate(service::findPage);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EnrolleeDTO enroll(@RequestBody EnrolleeDTO dto) {
    return service.create(dto);
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void unroll(@PathVariable Long id) {
    service.deleteById(id);
  }

  @GetMapping(path = "/{id}")
  public EnrolleeDTO getEnrolleeById(@PathVariable Long id) {
    return service.findOne(id);
  }

  @GetMapping
  public List<EnrolleeDTO> getEnrollees(
      @RequestParam(required = false) int page,
      @RequestParam(required = false) int size) {
    return pageRequest.apply(page, size);
  }

  @GetMapping(path = "/best")
  public List<EnrolleeDTO> getBestEnrollees() {
    return service.findBestEnrollees();
  }

}
