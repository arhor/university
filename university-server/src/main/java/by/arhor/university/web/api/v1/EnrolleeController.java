package by.arhor.university.web.api.v1;

import static by.arhor.university.web.api.util.PageUtils.paginate;
import static by.arhor.university.web.api.v1.ApiController.API_V_1;

import java.net.Authenticator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import by.arhor.university.service.EnrolleeService;
import by.arhor.university.service.UserService;
import by.arhor.university.service.dto.EnrolleeDTO;

@Lazy
@RestController
@RequestMapping(path = API_V_1 + "/enrollees")
public class EnrolleeController extends ApiController {

  private final EnrolleeService enrolleeService;
  private final UserService userService;

  @Autowired
  public EnrolleeController(EnrolleeService enrolleeService, UserService userService) {
    this.enrolleeService = enrolleeService;
    this.userService = userService;
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('USER')")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> enroll(@RequestBody EnrolleeDTO dto, WebRequest req, Authentication auth) {
    var principal = auth.getPrincipal();
    if (principal instanceof User) {
      var email = ((User) principal).getUsername();
      return handle(enrolleeService.create(dto, email), req.getLocale());
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("incompatible `principal` class provided in authentication");
  }

  @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public void unroll(@PathVariable Long id) {
    enrolleeService.deleteById(id);
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<?> getEnrolleeById(@PathVariable Long id, WebRequest req) {
    return handle(enrolleeService.findOne(id), req.getLocale());
  }

  @GetMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<EnrolleeDTO> getEnrollees(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return paginate(enrolleeService::findPage).apply(page, size);
  }

  @GetMapping(
      path = "/best",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<EnrolleeDTO> getBestEnrollees(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return paginate(enrolleeService::findBestEnrollees).apply(page, size);
  }
}
