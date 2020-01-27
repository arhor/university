package by.arhor.university.domain.repository;

import by.arhor.university.domain.model.Lang;

import java.util.List;
import java.util.Optional;

public interface LangRepository {

  Optional<Lang> findByLabel(String label);

  short getDefaultLangId();

  List<Lang> findAll();

}
