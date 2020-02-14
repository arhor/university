package by.arhor.university.service.impl;

import static by.arhor.core.Either.error;
import static by.arhor.university.service.error.ServiceError.alreadyExists;
import static by.arhor.university.service.error.ServiceError.notFound;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.arhor.core.Either;
import by.arhor.university.domain.model.Enrollee;
import by.arhor.university.domain.repository.EnrolleeRepository;
import by.arhor.university.domain.repository.UserRepository;
import by.arhor.university.service.EnrolleeService;
import by.arhor.university.service.dto.EnrolleeDTO;
import by.arhor.university.service.error.ServiceError;

@Service
@Transactional
public class EnrolleeServiceImpl extends AbstractService<Enrollee, EnrolleeDTO, Long>
    implements EnrolleeService {

  @Autowired private UserRepository userRepository;

  @Autowired
  public EnrolleeServiceImpl(EnrolleeRepository repository, ModelMapper mapper) {
    super(EnrolleeDTO.class, repository, mapper);
  }

  @Override
  public Either<EnrolleeDTO, ServiceError> create(EnrolleeDTO dto, String userEmail) {
    var userOptional = userRepository.findByEmail(userEmail);
    if (userOptional.isPresent()) {
      var user = userOptional.get();
      var enrollee = user.getEnrollee();
      if (enrollee == null) {
        var newEnrollee = mapper.map(dto, Enrollee.class);
        newEnrollee.setUser(user);
        var savedEnrollee = repository.save(newEnrollee);
        user.setEnrollee(savedEnrollee);
        return Either.success(toDto(savedEnrollee));
      } else {
        return error(alreadyExists("Enrollee", "user", userEmail));
      }
    } else {
      return error(notFound("User", "email", userEmail));
    }
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

  private EnrolleeRepository enrolleeRepository() {
    return (EnrolleeRepository) repository;
  }
}
