package by.arhor.university.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.arhor.core.Either;
import by.arhor.university.domain.model.Faculty;
import by.arhor.university.domain.repository.FacultyRepository;
import by.arhor.university.service.FacultyService;
import by.arhor.university.service.dto.FacultyDTO;
import by.arhor.university.service.error.ServiceError;

@Service
@Transactional
public class FacultyServiceImpl extends AbstractService<Faculty, FacultyDTO, Long> implements FacultyService {

  @Autowired
  public FacultyServiceImpl(FacultyRepository repository, ModelMapper mapper) {
    super(FacultyDTO.class, repository, mapper);
  }

  @Override
  public Either<FacultyDTO, ServiceError> create(FacultyDTO dto) {
    boolean exists = facultyRepository().existsByDefaultTitle(dto.getDefaultTitle());
    if (exists) {
      return Either.failure(null);
    }
    var newFaculty = mapper.map(dto, Faculty.class);
    var createdFaculty = repository.save(newFaculty);
    return Either.success(toDto(createdFaculty));
  }

  @Override
  public Either<FacultyDTO, ServiceError> update(FacultyDTO dto) {
    boolean exists = facultyRepository().existsByDefaultTitle(dto.getDefaultTitle());
    if (exists) {
      return Either.failure(null);
    }
    var faculty = repository.findById(dto.getId()).orElseThrow(RuntimeException::new);
    faculty.setDefaultTitle(dto.getDefaultTitle());
    faculty.setSeatsBudget(dto.getSeatsBudget());
    faculty.setSeatsPaid(dto.getSeatsPaid());
    var savedFaculty = repository.save(faculty);
    return Either.success(toDto(savedFaculty));
  }

  private FacultyRepository facultyRepository() {
    return (FacultyRepository) repository;
  }
}
