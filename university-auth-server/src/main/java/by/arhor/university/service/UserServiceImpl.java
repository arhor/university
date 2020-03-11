package by.arhor.university.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.arhor.university.domain.Role;
import by.arhor.university.domain.User;
import by.arhor.university.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final PasswordEncoder encoder;
  private final UserRepository repository;

  @Override
  public void create(User user) {
    repository
        .findByEmail(user.getEmail())
        .ifPresent(existing -> { throw new IllegalArgumentException("user already exists: " + existing.getId()); });

    user.setPassword(encoder.encode(user.getPassword()));

    var created = repository.save(user);

    log.info("new user has been created: {}", created.getId());
  }

  @Override
  public List<User> findAll() {
    var users = new ArrayList<User>();
    repository.findAll().forEach(users::add);
    return users;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository
        .findByEmail(username)
        .map(this::userToUserDetails)
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }

  private UserDetails userToUserDetails(User user) {
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        authoritiesFrom(user.getRole())
    );
  }

  private Collection<? extends GrantedAuthority> authoritiesFrom(Role role) {
    return List.of(new SimpleGrantedAuthority(role.getTitle().toString()));
  }
}
