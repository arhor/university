package by.arhor.university.service.trait;

import by.arhor.university.service.dto.DTO;

public interface Updater<T extends DTO> {

  T update(T item);

}
