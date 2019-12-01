package by.bsu.uir.university.domain.repository;

import by.arhor.university.domain.repository.EnrolleeRepository;
import by.arhor.university.domain.repository.FacultyRepository;
import by.arhor.university.domain.repository.LangRepository;
import by.arhor.university.domain.repository.RoleRepository;
import by.arhor.university.domain.repository.SubjectRepository;
import by.arhor.university.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepositoryTest {

  @Autowired private RoleRepository roleRepository;
  @Autowired private LangRepository langRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private EnrolleeRepository enrolleeRepository;
  @Autowired private FacultyRepository facultyRepository;
  @Autowired private SubjectRepository subjectRepository;

}
