package by.arhor.university.repository;

import java.util.Optional;

import by.arhor.university.model.Lang;

public interface LabelRepository {

  Optional<String> getLocalizedString(String label, Lang lang);

}
