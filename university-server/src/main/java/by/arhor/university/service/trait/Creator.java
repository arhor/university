package by.arhor.university.service.trait;

import by.arhor.university.core.Either;
import by.arhor.university.service.dto.DTO;
import by.arhor.university.service.error.ServiceError;

public interface Creator<T extends DTO<?>> {

  Either<T, ServiceError> create(T item);

}
