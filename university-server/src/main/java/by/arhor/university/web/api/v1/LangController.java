package by.arhor.university.web.api.v1;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.domain.model.Lang;
import by.arhor.university.domain.repository.LangRepository;

@Lazy
@RestController
@RequestMapping(path = "/api/v1/langs")
public class LangController {

  private final LangRepository repository;

  @Autowired
  public LangController(LangRepository repository) {
    this.repository = repository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Lang> getLangs() {
//    return repository.findAll();
    return Arrays.stream(Lang.Value.values()).map(Lang::new).collect(toList()); // FIXME: stub!
  }

  @GetMapping(path = "/default", produces = MediaType.APPLICATION_JSON_VALUE)
  public Lang getLangsDefault() {
    return repository.getDefaultLang();
  }

}
