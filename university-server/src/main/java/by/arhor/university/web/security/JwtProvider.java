package by.arhor.university.web.security;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

  private final ObjectMapper objectMapper;

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
    final var principal = (UserDetails) authentication.getPrincipal();
    final var payload = objectMapper.createObjectNode();
    final var roles = objectMapper.createArrayNode();

    final Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
    if (authorities != null) {
      for (GrantedAuthority authority : authorities) {
        roles.add(authority.getAuthority());
      }
    }

    payload.put("email", principal.getUsername());
    payload.set("roles", roles);

    var startTime = new Date();
    return Jwts.builder()
        .setSubject(payload.toString())
        .setIssuedAt(startTime = new Date())
        .setExpiration(new Date(startTime.getTime() + jwtExpiration))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) throws JsonProcessingException {
    final var subject = jwtParser.parseClaimsJws(token)
        .getBody()
        .getSubject();
    return objectMapper.readTree(subject)
        .findValue("email")
        .asText();
  }

  public boolean tokenIsValid(String authToken) {
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
