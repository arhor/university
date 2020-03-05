package by.arhor.university.web.security;

import lombok.Value;

@Value(staticConstructor = "withToken")
public class JwtResponse {
  String accessToken;
  String tokenType = "Bearer";
}
