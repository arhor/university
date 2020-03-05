package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.REST_API_V_1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.service.SubjectService;
import by.arhor.university.service.dto.SubjectDTO;

@Lazy
@RestController
@RequestMapping(path = REST_API_V_1 + "/subjects")
public class SubjectController extends ApiController {

  private final SubjectService service;

  @Autowired
  public SubjectController(SubjectService service) {
    this.service = service;
  }

  public List<SubjectDTO> getSubjects() {
    return service.findAll();
  }
}
