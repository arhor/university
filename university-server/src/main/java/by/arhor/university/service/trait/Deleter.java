package by.arhor.university.service.trait;

public interface Deleter<T, K> {

  void delete(T item);

  void deleteById(K id);

}
