package by.arhor.university.domain.repository;

import java.util.List;
import java.util.Optional;

import by.arhor.university.domain.model.Lang;

public interface LangRepository {

  Optional<Lang> findByLabel(String label);

  short getDefaultLangId();

  List<Lang> findAll();

}
