package by.arhor.university.web.api.v1;

import by.arhor.university.web.security.JwtProvider;
import by.arhor.university.domain.model.User;
import by.arhor.university.domain.repository.RoleRepository;
import by.arhor.university.domain.repository.UserRepository;
import by.arhor.university.web.security.JwtResponse;
import by.arhor.university.web.api.model.SignInRequest;
import by.arhor.university.web.api.model.SignUpRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final JwtProvider jwtProvider;
  private final ModelMapper mapper;

  @Autowired
  public AuthController(
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      PasswordEncoder encoder,
      JwtProvider jwtProvider,
      ModelMapper mapper) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.encoder = encoder;
    this.jwtProvider = jwtProvider;
    this.mapper = mapper;
  }

  @PostMapping("/signin")
  public JwtResponse authenticateUser(@RequestBody SignInRequest signInRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            signInRequest.getEmail(),
            signInRequest.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    return new JwtResponse(
        jwtProvider.generateJwtToken(authentication)
    );
  }

  @PostMapping("/signup")
  public ResponseEntity<String> registerUser(@RequestBody SignUpRequest signUpRequest) {
    if (userRepository.countByEmail(signUpRequest.getEmail()) != 0) {
      return new ResponseEntity<>("Fail -> Email is already taken!",
          HttpStatus.BAD_REQUEST);
    }

    // Creating user's account
    var newUser = userRepository.createNewUser(
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()),
        signUpRequest.getFirstName(),
        signUpRequest.getLastName()
    );

    System.out.println(newUser);

    return ResponseEntity.ok().body("User registered successfully!");
  }
}
