package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.CACHE_LANGS;
import static by.arhor.university.Constants.REST_API_V_1;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.model.Lang;
import by.arhor.university.repository.LangRepository;

@Lazy
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = REST_API_V_1 + "/langs")
public class LangController extends ApiController {

  private final LangRepository repository;

  @Cacheable(cacheNames = CACHE_LANGS)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Lang> getLangs() {
    return repository.findAll();
  }

  @GetMapping(path = "/default", produces = MediaType.APPLICATION_JSON_VALUE)
  public Lang.Value getDefaultLang() {
    return repository.getDefaultLang().map(Lang::getLabel).orElse(Lang.Value.RU);
  }
}
