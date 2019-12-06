package by.arhor.university.service.trait;

import java.util.List;

import by.arhor.university.service.dto.DTO;

public interface Reader<T extends DTO, K> {

  T findOne(K id);

  List<T> findAll();

  List<T> findPage(int page, int size);

}
