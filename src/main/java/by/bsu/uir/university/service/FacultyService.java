package by.bsu.uir.university.service;

import by.bsu.uir.university.service.dto.FacultyDto;
import by.bsu.uir.university.service.trait.Creator;
import by.bsu.uir.university.service.trait.Deleter;
import by.bsu.uir.university.service.trait.Reader;
import by.bsu.uir.university.service.trait.Updater;

public interface FacultyService
    extends Creator<FacultyDto>
          , Reader<FacultyDto, Long>
          , Updater<FacultyDto>
          , Deleter<FacultyDto, Long> {
}
