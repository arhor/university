package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.REST_API_V_1;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.service.SubjectService;
import by.arhor.university.service.dto.SubjectDTO;

@Lazy
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = REST_API_V_1 + "/subjects")
public class SubjectController extends ApiController {

  private final SubjectService service;

  public List<SubjectDTO> getSubjects() {
    return service.findAll();
  }
}
