package by.arhor.university.service.impl;

import by.arhor.university.domain.model.Faculty;
import by.arhor.university.domain.repository.FacultyRepository;
import by.arhor.university.service.dto.FacultyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Service
@Transactional
public class FacultyServiceImpl
    extends AbstractService<Faculty, FacultyDTO, Long> {

  @Autowired
  public FacultyServiceImpl(FacultyRepository repository, ModelMapper mapper) {
    super(FacultyDTO.class, repository, mapper);
  }
}
