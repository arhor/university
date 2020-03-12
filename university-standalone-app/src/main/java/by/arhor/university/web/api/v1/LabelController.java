package by.arhor.university.web.api.v1;

import static by.arhor.university.Constants.REST_API_V_1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arhor.university.service.LabelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = REST_API_V_1 + "/labels")
public class LabelController extends ApiController {

  private final LabelService service;

}
