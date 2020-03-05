package by.arhor.university.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.arhor.university.web.filter.CustomCsrfFilter;

@Configuration
public class UtilBeansConfig {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(5);
  }

  @Bean
  public MessageSource messageSource() {
    final var messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages");
    return messageSource;
  }

  @Bean
  @Profile({"!dev"})
  public FilterRegistrationBean<CustomCsrfFilter> csrfFilter() {
    final var registrationBean = new FilterRegistrationBean<CustomCsrfFilter>();
    registrationBean.setFilter(new CustomCsrfFilter());
    registrationBean.addUrlPatterns("/api/*");
    return registrationBean;
  }
}
