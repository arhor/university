package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.REST_API_V_1;
import static by.arhor.university.web.api.util.PageUtils.bound;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.service.SubjectService;
import by.arhor.university.service.dto.SubjectDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Lazy
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = REST_API_V_1 + "/subjects")
public class SubjectController extends ApiController {

  private final SubjectService service;

  @GetMapping(produces = "application/json")
  public List<SubjectDTO> getSubjects(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return bound(service::findPage).apply(page, size);
  }
}
