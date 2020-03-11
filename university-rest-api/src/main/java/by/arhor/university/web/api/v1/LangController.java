package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.CACHE_LANGS;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.model.Lang;
import by.arhor.university.repository.LangRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Lazy
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/langs")
public class LangController extends ApiController {

  private final LangRepository repository;

  @Cacheable(cacheNames = CACHE_LANGS)
  @GetMapping(produces = "application/json")
  public List<Lang> getLangs() {
    return repository.findAll();
  }

  @GetMapping(path = "/default", produces = "application/json")
  public Lang.Value getDefaultLang() {
    return repository.getDefaultLang().map(Lang::getLabel).orElse(Lang.Value.RU);
  }
}
