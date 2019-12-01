package by.arhor.university.service;

import by.arhor.university.service.dto.FacultyDto;
import by.arhor.university.service.trait.Creator;
import by.arhor.university.service.trait.Deleter;
import by.arhor.university.service.trait.Reader;
import by.arhor.university.service.trait.Updater;

public interface FacultyService
    extends Creator<FacultyDto>
          , Reader<FacultyDto, Long>
          , Updater<FacultyDto>
          , Deleter<FacultyDto, Long> {
}
