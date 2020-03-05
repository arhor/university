package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.CACHE_LANGS;
import static by.arhor.university.Constants.REST_API_V_1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.domain.model.Lang;
import by.arhor.university.domain.repository.LangRepository;

@Lazy
@RestController
@RequestMapping(path = REST_API_V_1 + "/langs")
public class LangController extends ApiController {

  private final LangRepository repository;

  @Autowired
  public LangController(LangRepository repository) {
    this.repository = repository;
  }

  @Cacheable(cacheNames = CACHE_LANGS)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Lang> getLangs() {
    return repository.findAll();
  }

  @GetMapping(path = "/default", produces = MediaType.APPLICATION_JSON_VALUE)
  public short getDefaultLangId() {
    return repository.getDefaultLangId();
  }
}
