package by.arhor.university.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.arhor.university.domain.model.User;
import by.arhor.university.domain.repository.RoleRepository;
import by.arhor.university.domain.repository.UserRepository;
import by.arhor.university.service.UserService;
import by.arhor.university.service.dto.UserDTO;

@Lazy
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

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
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return repository
        .findByEmail(email)
        .map(this::userToUserDetails)
        .orElseThrow(RuntimeException::new);
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

    final var encoded = encoder.encode(newUser.getPassword());
    final var defaultRole = roleRepository.getDefaultRole();

    newUser.setPassword(encoded);
    newUser.setRole(defaultRole);

    final var savedUser = repository.save(newUser);
    return mapper.map(savedUser, UserDTO.class);
  }

  private void checkForDuplicates(String email) {
    if (repository.countByEmail(email) > 0)
      throw new RuntimeException();
  }

  @Override
  public void delete(UserDTO item) {
    deleteById(item.getId());
  }

  @Override
  public void deleteById(Long id) {
    final var user = repository
        .findById(id)
        .orElseThrow(RuntimeException::new);
    repository.delete(user);
  }

  @Override
  @Transactional(readOnly = true)
  public UserDTO findOne(Long id) {
    return repository
        .findById(id)
        .map(user -> mapper.map(user, UserDTO.class))
        .orElseThrow(RuntimeException::new);
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserDTO> findAll() {
    return repository
        .findAll()
        .stream()
        .map(user -> mapper.map(user, UserDTO.class))
        .collect(toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserDTO> findPage(int page, int size) {
    return repository
        .findAll(PageRequest.of(page, size))
        .stream()
        .map(user -> mapper.map(user, UserDTO.class))
        .collect(toList());
  }

  @Override
  public UserDTO update(UserDTO item) {
    return null;
  }

}
