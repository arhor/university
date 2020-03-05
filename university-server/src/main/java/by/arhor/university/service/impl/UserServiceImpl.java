package by.arhor.university.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.arhor.university.core.Either;
import by.arhor.university.core.function.RichSupplier;
import by.arhor.university.model.User;
import by.arhor.university.repository.UserRepository;
import by.arhor.university.service.UserService;
import by.arhor.university.service.dto.UserDTO;
import by.arhor.university.service.error.ServiceError;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository repository;
  private final PasswordEncoder encoder;
  private final ModelMapper mapper;

  @Autowired
  public UserServiceImpl(
      UserRepository repository,
      PasswordEncoder encoder,
      ModelMapper mapper) {
    this.repository = repository;
    this.encoder = encoder;
    this.mapper = mapper;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return repository
        .findByEmail(email)
        .map(this::userToUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException("there is no user with email: " + email));
  }

  private UserDetails userToUserDetails(User user) {
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(), // fixme: should I encode password here or just pass it raw?
        authoritiesFrom(user.getRole()::getTitle)
    );
  }

  private Collection<? extends GrantedAuthority> authoritiesFrom(RichSupplier<?> source) {
    return source
        .map(Object::toString)
        .map(SimpleGrantedAuthority::new)
        .map(List::of)
        .get();
  }

  @Override
  public Either<UserDTO, ServiceError> create(UserDTO userDto) {
    if (emailAlreadyTaken(userDto.getEmail())) {
      return Either.failure(null);
    }

    final var newUser = repository.createNewUser(
        userDto.getEmail(),
        encoder.encode(userDto.getPassword()),
        userDto.getFirstName(),
        userDto.getLastName()
    );

    return Either.success(mapper.map(newUser, UserDTO.class));
  }

  private boolean emailAlreadyTaken(String email) {
    return repository.countByEmail(email) > 0;
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
  public Either<UserDTO, ServiceError> findOne(Long id) {
    return repository
        .findById(id)
        .map(user -> mapper.map(user, UserDTO.class))
        .map(Either::<UserDTO, ServiceError>success)
        .orElseGet(() -> Either.failure(ServiceError.notFound("User", "id", id)));
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
  public Either<UserDTO, ServiceError> update(UserDTO userDto) {
    return null;
  }

}
