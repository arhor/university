package by.bsu.uir.university.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepositoryTest {

  @Autowired private RoleRepository roleRepository;
  @Autowired private LangRepository langRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private EnrolleeRepository enrolleeRepository;
  @Autowired private FacultyRepository facultyRepository;
  @Autowired private SubjectRepository subjectRepository;

}
