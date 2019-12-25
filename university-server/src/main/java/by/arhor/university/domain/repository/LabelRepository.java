package by.arhor.university.domain.repository;

import by.arhor.university.domain.model.Lang;

import java.util.Optional;

public interface LabelRepository {

  Optional<String> getLocalizedString(String label, Lang lang);

}
