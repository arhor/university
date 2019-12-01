package by.arhor.university.service;

import by.arhor.university.service.dto.FacultyDTO;
import by.arhor.university.service.trait.Creator;
import by.arhor.university.service.trait.Deleter;
import by.arhor.university.service.trait.Reader;
import by.arhor.university.service.trait.Updater;

public interface FacultyService
    extends Creator<FacultyDTO>
          , Reader<FacultyDTO, Long>
          , Updater<FacultyDTO>
          , Deleter<FacultyDTO, Long> {
}
