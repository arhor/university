package by.arhor.university.repository;

import java.util.List;
import java.util.Optional;

import by.arhor.university.model.Lang;

public interface LangRepository {

  Optional<Lang> findLangByLabel(String label);

  Optional<Lang> getDefaultLang();

  List<Lang> findAll();

}
