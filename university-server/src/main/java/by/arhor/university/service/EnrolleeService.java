package by.arhor.university.service;

import java.util.List;

import by.arhor.university.service.dto.EnrolleeDTO;
import by.arhor.university.service.trait.Creator;
import by.arhor.university.service.trait.Deleter;
import by.arhor.university.service.trait.Reader;
import by.arhor.university.service.trait.Updater;

public interface EnrolleeService
    extends Creator<EnrolleeDTO>
          , Reader<EnrolleeDTO, Long>
          , Updater<EnrolleeDTO>
          , Deleter<EnrolleeDTO, Long> {

  List<EnrolleeDTO> findBestEnrollees(int page, int size);

}
