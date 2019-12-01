package by.arhor.university.service.impl;

import by.arhor.university.domain.model.User;
import by.arhor.university.domain.repository.RoleRepository;
import by.arhor.university.domain.repository.UserRepository;
import by.arhor.university.service.UserService;
import by.arhor.university.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Lazy
@Service
public class UserServiceImpl implements UserService {

  private final PasswordEncoder encoder;
  private final ModelMapper mapper;
  private final UserRepository repository;
  private final RoleRepository roleRepository;

  @Autowired
  public UserServiceImpl(
      PasswordEncoder encoder,
      ModelMapper mapper,
      UserRepository repository,
      RoleRepository roleRepository) {
    this.encoder = encoder;
    this.mapper = mapper;
    this.repository = repository;
    this.roleRepository = roleRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return repository
        .findByEmail(email)
        .map(this::userToUserDetails)
        .orElseThrow(() -> new RuntimeException());
  }

  private UserDetails userToUserDetails(User user) {
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(), // fixme: should I encode password here or just pass it raw?
        List.of(
            new SimpleGrantedAuthority(
                user.getRole().toString())));
  }

  @Override
  public UserDTO create(UserDTO userDto) {
    checkForDuplicates(userDto.getEmail());
    final var newUser = mapper.map(userDto, User.class);
    final var savedUser = repository.save(newUser);
    return mapper.map(savedUser, UserDTO.class);
  }

  private void checkForDuplicates(String email) {
    if (repository.countByEmail(email) > 0)
      throw new RuntimeException();
  }

  @Override
  public void delete(UserDTO item) {

  }

  @Override
  public void deleteById(Long id) {

  }

  @Override
  public UserDTO findOne(Long id) {
    return null;
  }

  @Override
  public List<UserDTO> findAll() {
    return null;
  }

  @Override
  public List<UserDTO> findPage(int page, int size) {
    return null;
  }

  @Override
  public UserDTO update(UserDTO item) {
    return null;
  }

}
