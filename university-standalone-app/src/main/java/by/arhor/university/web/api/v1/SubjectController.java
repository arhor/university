package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.REST_API_V_1;
import static by.arhor.university.web.api.util.PageUtils.bound;

import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
@RequestMapping(
    path = REST_API_V_1 + "/subjects",
    produces = MediaType.APPLICATION_JSON_VALUE)
public class SubjectController extends ApiController {

  private final SubjectService service;

  @GetMapping
  public List<SubjectDTO> allSubjects(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return bound(service::findPage).apply(page, size);
  }

  @GetMapping("/my")
  public List<SubjectDTO> mySubjects(Authentication auth, Locale locale) {
    var user = (User) auth.getPrincipal();
    var email = user.getUsername();
    return service.findSubjectsByEnrolleeEmail(email);
  }
}
