package by.arhor.university.service.impl;

import by.arhor.university.domain.repository.LabelRepository;
import by.arhor.university.domain.repository.LangRepository;
import by.arhor.university.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LabelServiceImpl implements LabelService {

  private final LabelRepository labelRepository;
  private final LangRepository langRepository;

  @Autowired
  public LabelServiceImpl(LabelRepository labelRepository, LangRepository langRepository) {
    this.labelRepository = labelRepository;
    this.langRepository = langRepository;
  }

  @Override
  public String localize(String label, Locale locale) {
//    return labelRepository.getLocalizedString(
//        label,
//        langRepository.findByLabel(locale.getLanguage()).orElseGet(langRepository::getDefaultLang)
//    ).orElseThrow();
    return "not implemented yet =)";
  }
}
