package by.arhor.university.web.filter;

import by.arhor.core.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
  * Class CustomCsrfFilter implements stateless CSRF protection. To successfully
  * pass a CSRF check, the request must contain the CSRF token generated on the
  * client side in the appropriate header and cookie, and the token in the header
  * must match the token in the cookie.
  *
  * @author Maksim Buryshynets
  * @version 1.0 11 March 2019
  */
@Component
@Profile({"!dev"})
public class CustomCsrfFilter extends OncePerRequestFilter {

  private static final String ERROR_MSG = "CSRF token is missing or not matching";
  private static final String CSRF_COOKIE = "XSRF-TOKEN";
  private static final String CSRF_HEADER = "X-XSRF-TOKEN";

  private final AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
  private final Lazy<Pattern> safeMethod = Lazy.eval(() -> Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$"));

  @Override
  protected void doFilterInternal(
      HttpServletRequest req,
      HttpServletResponse res,
      FilterChain filterChain) throws IOException, ServletException {

    final var method = req.getMethod();
    final var isTokenRequired = safeMethod.get().matcher(method).matches();

    if (isTokenRequired) {
      final var csrfHeaderToken = req.getHeader(CSRF_HEADER);
      final var csrfCookieToken = getCsrfCookieToken(req);

      if ((csrfCookieToken == null) || !csrfCookieToken.equals(csrfHeaderToken)) {
        accessDeniedHandler.handle(req, res, new AccessDeniedException(ERROR_MSG));
      }
    }
    filterChain.doFilter(req, res);
  }

  private String getCsrfCookieToken(HttpServletRequest req) {
    final var cookies = req.getCookies();
    if (cookies != null) {
      for (var c : cookies) {
        if ((c != null) && CSRF_COOKIE.equals(c.getName())) {
          return c.getValue();
        }
      }
    }
    return null;
  }
}
