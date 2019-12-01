package by.arhor.university.service.trait;

import java.util.List;

public interface Reader<T, K> {

  T findOne(K id);

  List<T> findAll();

  List<T> findPage(int page, int size);

}
