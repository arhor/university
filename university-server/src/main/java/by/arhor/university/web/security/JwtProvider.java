package by.arhor.university.web.security;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

  private static final Logger log = LoggerFactory.getLogger(JwtProvider.class);

  private static final SignatureAlgorithm ALGORITHM;

  static {
    final int woof = ThreadLocalRandom.current().nextInt(2);
    switch (woof) {
      case 0:  ALGORITHM = SignatureAlgorithm.HS256; break;
      case 1:  ALGORITHM = SignatureAlgorithm.HS384; break;
      default: ALGORITHM = SignatureAlgorithm.HS512;
    }
  }

  @Value("${security.jwt.secret}")
  private String jwtSecret;

  @Value("${security.jwt.expire}")
  private int jwtExpiration;

  private JwtParser jwtParser;

  @PostConstruct
  public void init() {
    jwtParser = Jwts.parser().setSigningKey(jwtSecret);
  }

  public String generateJwtToken(Authentication authentication) {
    var principal = (UserDetails) authentication.getPrincipal();
    var startTime = new Date();
    return Jwts.builder()
        .setSubject(principal.getUsername())
        .setIssuedAt(startTime = new Date())
        .setExpiration(new Date(startTime.getTime() + jwtExpiration))
        .signWith(ALGORITHM, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return jwtParser
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      jwtParser.parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      log.error("Invalid JWT signature -> Message: {} ", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token -> Message: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token -> Message: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token -> Message: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty -> Message: {}", e.getMessage());
    }
    return false;
  }
}
