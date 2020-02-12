package by.arhor.university.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.arhor.core.Either;
import by.arhor.university.domain.model.Enrollee;
import by.arhor.university.domain.repository.EnrolleeRepository;
import by.arhor.university.service.EnrolleeService;
import by.arhor.university.service.dto.EnrolleeDTO;
import by.arhor.university.service.error.ServiceError;

@Lazy
@Service
@Transactional
public class EnrolleeServiceImpl extends AbstractService<Enrollee, EnrolleeDTO, Long>
    implements EnrolleeService {

  @Autowired
  public EnrolleeServiceImpl(EnrolleeRepository repository, ModelMapper mapper) {
    super(EnrolleeDTO.class, repository, mapper);
  }

  @Override
  public Either<EnrolleeDTO, ServiceError> create(EnrolleeDTO item) {
    return null;
  }

  @Override
  public void delete(EnrolleeDTO item) {}

  @Override
  public void deleteById(Long id) {}

  @Override
  public List<EnrolleeDTO> findAll() {
    return null;
  }

  @Override
  public List<EnrolleeDTO> findPage(int page, int size) {
    return null;
  }

  @Override
  public Either<EnrolleeDTO, ServiceError> update(EnrolleeDTO item) {
    return null;
  }

  @Override
  public List<EnrolleeDTO> findBestEnrollees(int page, int size) {
    return null;
  }
}
