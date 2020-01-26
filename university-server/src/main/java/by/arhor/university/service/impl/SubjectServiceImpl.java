package by.arhor.university.service.impl;

import by.arhor.university.domain.model.Subject;
import by.arhor.university.domain.repository.SubjectRepository;
import by.arhor.university.service.SubjectService;
import by.arhor.university.service.dto.SubjectDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubjectServiceImpl extends AbstractService<Subject, SubjectDTO, Long> implements SubjectService {

  @Autowired
  public SubjectServiceImpl(SubjectRepository repository, ModelMapper mapper) {
    super(SubjectDTO.class, repository, mapper);
  }


  private SubjectRepository subjectRepository() {
    return (SubjectRepository) repository;
  }
}
