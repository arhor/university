package by.arhor.university.web.api.v1;

import static by.arhor.university.web.api.v1.Root.API_V_1;

import by.arhor.university.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = API_V_1 + "/labels")
public class LabelController {

  private final LabelService service;

  @Autowired
  public LabelController(LabelService service) {
    this.service = service;
  }

}
