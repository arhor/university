package by.arhor.university.web.api.v1;

import static by.arhor.university.web.api.v1.ApiController.API_V_1;

import by.arhor.university.domain.model.Lang;
import by.arhor.university.domain.repository.LangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Lazy
@RestController
@RequestMapping(path = API_V_1 + "/langs")
public class LangController extends ApiController {

  private final LangRepository repository;

  @Autowired
  public LangController(LangRepository repository) {
    this.repository = repository;
  }

  @Cacheable(cacheNames = "cache_langs")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Lang> getLangs() {
    return repository.findAll();
  }

  @GetMapping(path = "/default", produces = MediaType.APPLICATION_JSON_VALUE)
  public short getDefaultLangId() {
    return repository.getDefaultLangId();
  }
}
