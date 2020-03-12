package by.arhor.university.service.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.arhor.university.repository.LabelRepository;
import by.arhor.university.repository.LangRepository;
import by.arhor.university.service.LabelService;

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
