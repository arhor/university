package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.REST_API_V_1;
import static by.arhor.university.web.api.util.PageUtils.bound;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

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

import by.arhor.university.core.Either;
import by.arhor.university.service.EnrolleeService;
import by.arhor.university.service.dto.EnrolleeDTO;
import by.arhor.university.service.error.ServiceError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Lazy
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(
    path = REST_API_V_1 + "/enrollees",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class EnrolleeController extends ApiController {

  private final EnrolleeService enrolleeService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('USER')")
  public ResponseEntity<?> enroll(
      @RequestBody EnrolleeDTO dto,
      WebRequest req,
      Authentication auth) {

    var principal = auth.getPrincipal();

    if (principal instanceof User) {
      var email = ((User) principal).getUsername();
      var serviceResponse = enrolleeService.create(dto, email);
      return handle(serviceResponse, req.getLocale());
    }

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("incompatible `principal` class provided in authentication");
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
  public void unroll(@PathVariable long id) {
    enrolleeService.deleteById(id);
  }

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<?> getEnrolleeById(@PathVariable long id, Locale locale) {
    return handle(enrolleeService.findOne(id), locale);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<EnrolleeDTO> getEnrollees(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return bound(enrolleeService::findPage).apply(page, size);
  }

  @GetMapping("/best")
  @PreAuthorize("hasAuthority('ADMIN')")
  public List<EnrolleeDTO> getBestEnrollees(
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer size) {
    return bound(enrolleeService::findBestEnrollees).apply(page, size);
  }

  @PostMapping("/{enrolleeId}")
  public ResponseEntity<?> addEnrolleeSubject(
      @PathVariable long enrolleeId,
      @RequestParam long subjectId,
      @RequestParam short score,
      Locale locale) {

    return handle(
        enrolleeService.addEnrolleeSubject(
            enrolleeId,
            subjectId,
            score
        ),
        locale
    );
  }
}
