package by.arhor.university.service.trait;

import java.util.List;

import by.arhor.university.core.Either;
import by.arhor.university.service.dto.DTO;
import by.arhor.university.service.error.ServiceError;

public interface Reader<T extends DTO, K> {

  Either<T, ServiceError> findOne(K id);

  List<T> findAll();

  List<T> findPage(int page, int size);

}
