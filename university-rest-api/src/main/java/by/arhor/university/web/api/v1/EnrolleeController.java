package by.arhor.university.web.api.v1;

import static by.arhor.university.web.api.util.PageUtils.bound;

import java.util.List;
import java.util.Locale;

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
import by.arhor.university.service.dto.EnrolleeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Lazy
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/enrollees")
public class EnrolleeController extends ApiController {

  private final EnrolleeService enrolleeService;

  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('USER')")
  @PostMapping(consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> enroll(
      @RequestBody EnrolleeDTO dto,
      WebRequest req,
      Authentication auth) {

    var principal = auth.getPrincipal();

    if (principal instanceof User) {
      var email = ((User) principal).getUsername();
      return handle(enrolleeService.create(dto, email), req.getLocale());
    }

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("incompatible `principal` class provided in authentication");
  }

  @DeleteMapping(path = "/{id}", produces = "application/json")
  @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
  @ResponseStatus(HttpStatus.OK)
  public void unroll(@PathVariable Long id) {
    enrolleeService.deleteById(id);
  }

  @GetMapping(path = "/{id}", produces = "application/json")
//  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<?> getEnrolleeById(@PathVariable Long id, WebRequest req) {
    return handle(enrolleeService.findOne(id), req.getLocale());
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping(produces = "application/json")
  public List<EnrolleeDTO> getEnrollees(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return bound(enrolleeService::findPage).apply(page, size);
  }

  @GetMapping(path = "/best", produces = "application/json")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<EnrolleeDTO> getBestEnrollees(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return bound(enrolleeService::findBestEnrollees).apply(page, size);
  }

  @PostMapping(path = "/{enrolleeId}")
  public ResponseEntity<?> addEnrolleeSubject(
      @PathVariable Long enrolleeId,
      @RequestParam Long subjectId,
      @RequestParam Short score, Locale locale) {

    return handle(enrolleeService.addEnrolleeSubject(enrolleeId, subjectId, score), locale);
  }
}
