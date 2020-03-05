package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.REST_API_V_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.service.LabelService;

@RestController
@RequestMapping(path = REST_API_V_1 + "/labels")
public class LabelController extends ApiController {

  private final LabelService service;

  @Autowired
  public LabelController(LabelService service) {
    this.service = service;
  }

}
