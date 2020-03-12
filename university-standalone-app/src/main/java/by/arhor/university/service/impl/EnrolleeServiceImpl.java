package by.arhor.university.service.impl;

import static by.arhor.university.core.Either.failure;
import static by.arhor.university.core.Either.success;
import static by.arhor.university.service.error.ServiceError.alreadyExists;
import static by.arhor.university.service.error.ServiceError.notFound;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.arhor.university.core.Either;
import by.arhor.university.model.Enrollee;
import by.arhor.university.model.EnrolleeSubject;
import by.arhor.university.repository.EnrolleeRepository;
import by.arhor.university.repository.SubjectRepository;
import by.arhor.university.repository.UserRepository;
import by.arhor.university.service.EnrolleeService;
import by.arhor.university.service.dto.EnrolleeDTO;
import by.arhor.university.service.error.ErrorLabel;
import by.arhor.university.service.error.ServiceError;

@Service
@Transactional
public class EnrolleeServiceImpl extends AbstractService<Enrollee, EnrolleeDTO, Long>
    implements EnrolleeService {

  @Autowired private UserRepository userRepository;
  @Autowired private EnrolleeRepository enrolleeRepository;
  @Autowired private SubjectRepository subjectRepository;

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
        var savedEnrollee = enrolleeRepository.save(newEnrollee);
        user.setEnrollee(savedEnrollee);
        return Either.success(toDto(savedEnrollee));
      } else {
        return failure(alreadyExists("Enrollee", "user", userEmail));
      }
    } else {
      return failure(notFound("User", "email", userEmail));
    }
  }

  @Override
  public Either<EnrolleeDTO, ServiceError> addEnrolleeSubject(Long enrolleeId, Long subjectId, Short score) {
    var optionalEnrollee = enrolleeRepository.findById(enrolleeId);
    if (optionalEnrollee.isEmpty()) {
      return failure(notFound("optionalEnrollee", "id", enrolleeId));
    }

    var enrollee = optionalEnrollee.get();

    boolean enrolleeSubjectExists =
        enrollee.getEnrolleeSubjects().stream()
            .map(EnrolleeSubject::getId)
            .map(EnrolleeSubject.CompositeId::getSubjectId)
            .anyMatch(id -> Objects.equals(id, subjectId));

    if (enrolleeSubjectExists) {
      return failure(new ServiceError() {
        @Override
        public ErrorLabel getErrorLabel() {
          return ErrorLabel.ALREADY_EXISTS;
        }

        @Override
        public Object[] props() {
          return new Object[] { "Enrollee", "EnrolleeSubject", subjectId };
        }
      });
    }

    var optionalSubject = subjectRepository.findById(subjectId);
    if (optionalSubject.isEmpty()) {
      return failure(notFound("optionalSubject", "id", subjectId));
    }

    var subject = optionalSubject.get();

    enrollee
        .getEnrolleeSubjects()
        .add(
            new EnrolleeSubject(
                new EnrolleeSubject.CompositeId(enrolleeId, subjectId),
                enrollee,
                subject,
                score
            )
        );

    Enrollee savedEnrollee = enrolleeRepository.save(enrollee);

    return success(toDto(savedEnrollee));
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
    return (EnrolleeRepository) enrolleeRepository;
  }
}
