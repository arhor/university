package by.arhor.university.service.impl;

import by.arhor.university.model.Subject;
import by.arhor.university.repository.SubjectRepository;
import by.arhor.university.service.SubjectService;
import by.arhor.university.service.dto.SubjectDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class SubjectServiceImpl extends AbstractService<Subject, SubjectDTO, Long> implements SubjectService {

  @Autowired
  private SubjectRepository repository;

  @Autowired
  public SubjectServiceImpl(SubjectRepository repository, ModelMapper mapper) {
    super(SubjectDTO.class, repository, mapper);
  }

  @Override
  public List<SubjectDTO> findSubjectsByEnrolleeEmail(String email) {
    return repository.findSubjectsByUserEmail(email)
        .stream()
        .map(this::toDto)
        .collect(toList());
  }
}
