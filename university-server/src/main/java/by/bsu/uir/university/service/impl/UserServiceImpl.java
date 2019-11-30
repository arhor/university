package by.bsu.uir.university.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import by.bsu.uir.university.domain.model.Role;
import by.bsu.uir.university.domain.model.User;
import by.bsu.uir.university.domain.repository.RoleRepository;
import by.bsu.uir.university.domain.repository.UserRepository;
import by.bsu.uir.university.service.UserService;
import by.bsu.uir.university.service.dto.UserDto;
import lombok.RequiredArgsConstructor;

@Lazy
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final PasswordEncoder encoder;
  private final ModelMapper mapper;
  private final UserRepository repository;
  private final RoleRepository roleRepository;

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
  public UserDto create(UserDto userDto) {
    checkForDuplicates(userDto.getEmail());
    final var newUser = mapper.map(userDto, User.class);
    final var savedUser = repository.save(newUser);
    return mapper.map(savedUser, UserDto.class);
  }

  private void checkForDuplicates(String email) {
    if (repository.countByEmail(email) > 0)
      throw new RuntimeException();
  }

  @Override
  public void delete(UserDto item) {

  }

  @Override
  public void deleteById(Long id) {

  }

  @Override
  public UserDto findOne(Long id) {
    return null;
  }

  @Override
  public List<UserDto> findAll() {
    return null;
  }

  @Override
  public List<UserDto> findPage(int page, int size) {
    return null;
  }

  @Override
  public UserDto update(UserDto item) {
    return null;
  }

}
