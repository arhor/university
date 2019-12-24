package by.arhor.university.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.arhor.university.domain.model.Faculty;
import by.arhor.university.domain.repository.FacultyRepository;
import by.arhor.university.service.FacultyService;
import by.arhor.university.service.dto.FacultyDTO;

@Service
@Transactional
public class FacultyServiceImpl
    extends AbstractService<Faculty, FacultyDTO, Long>
    implements FacultyService {

  @Autowired
  public FacultyServiceImpl(FacultyRepository repository, ModelMapper mapper) {
    super(FacultyDTO.class, repository, mapper);
  }

  @Override
  public FacultyDTO create(FacultyDTO item) {
    return null;
  }

  @Override
  public FacultyDTO update(FacultyDTO item) {
    return null;
  }
}
