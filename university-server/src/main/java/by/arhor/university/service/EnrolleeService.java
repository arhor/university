package by.arhor.university.service;

import java.util.List;

import by.arhor.university.core.Either;
import by.arhor.university.service.dto.EnrolleeDTO;
import by.arhor.university.service.error.ServiceError;
import by.arhor.university.service.trait.Deleter;
import by.arhor.university.service.trait.Reader;
import by.arhor.university.service.trait.Updater;

public interface EnrolleeService
    extends Reader<EnrolleeDTO, Long>
          , Updater<EnrolleeDTO>
          , Deleter<EnrolleeDTO, Long> {

  List<EnrolleeDTO> findBestEnrollees(int page, int size);

  Either<EnrolleeDTO, ServiceError> create(EnrolleeDTO dto, String userEmail);

}
