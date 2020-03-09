package by.arhor.university.service.trait;

import by.arhor.university.service.dto.DTO;

public interface Deleter<T extends DTO, K> {

  void delete(T item);

  void deleteById(K id);

}
