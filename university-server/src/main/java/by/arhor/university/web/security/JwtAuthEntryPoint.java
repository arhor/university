package by.arhor.university.web.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  private static final Logger log = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

  @Override
  public void commence(
      HttpServletRequest req,
      HttpServletResponse res,
      AuthenticationException e) throws IOException {
    e.printStackTrace();
    log.error("Unauthorized error. Message - {}", e.getMessage());
    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
  }
}
