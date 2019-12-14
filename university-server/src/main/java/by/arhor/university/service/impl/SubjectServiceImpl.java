package by.arhor.university.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.arhor.university.domain.model.Subject;
import by.arhor.university.domain.repository.SubjectRepository;
import by.arhor.university.service.SubjectService;
import by.arhor.university.service.dto.SubjectDTO;

@Lazy
@Service
@Transactional
public class SubjectServiceImpl
    extends AbstractService<Subject, SubjectDTO, Long>
    implements SubjectService {

  @Autowired
  public SubjectServiceImpl(SubjectRepository repository, ModelMapper mapper) {
    super(SubjectDTO.class, repository, mapper);
  }

}
