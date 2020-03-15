package by.arhor.university.service;

import by.arhor.university.service.dto.SubjectDTO;
import by.arhor.university.service.trait.Reader;

import java.util.List;

public interface SubjectService extends Reader<SubjectDTO, Long> {

  List<SubjectDTO> findSubjectsByEnrolleeEmail(String email);

}
