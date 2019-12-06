package by.arhor.university.service.trait;

import by.arhor.university.service.dto.DTO;

public interface Creator<T extends DTO> {

  T create(T item);

}
