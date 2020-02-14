package by.arhor.university.service.trait;

import by.arhor.core.Either;
import by.arhor.university.service.dto.DTO;
import by.arhor.university.service.error.ServiceErrorImpl;

public interface Creator<T extends DTO<?>> {

  Either<T, ServiceErrorImpl> create(T item);

}
