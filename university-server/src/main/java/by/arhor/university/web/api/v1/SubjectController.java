package by.arhor.university.web.api.v1;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.domain.model.Subject;
import by.arhor.university.service.SubjectService;

@Lazy
@RestController
@RequestMapping(path = "/api/v1/subjects")
public class SubjectController {

  private final SubjectService service;

  public SubjectController(SubjectService service) {
    this.service = service;
  }

  public List<SubjectDTO> getSubjects() {
    return
  }

}
