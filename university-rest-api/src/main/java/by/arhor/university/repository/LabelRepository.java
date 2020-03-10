package by.arhor.university.repository;

import by.arhor.university.model.Lang;

import java.util.Optional;

public interface LabelRepository {

  Optional<String> getLocalizedString(String label, Lang lang);

}
