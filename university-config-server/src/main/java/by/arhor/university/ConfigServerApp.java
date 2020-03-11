package by.arhor.university;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableConfigServer
@SpringBootApplication
public class ConfigServerApp {

  public static void main(String[] args) {
    SpringApplication.run(ConfigServerApp.class, args);
  }

  @Configuration
  public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable()
          .authorizeRequests()
          .antMatchers("/actuator/**").permitAll()
          .anyRequest().authenticated()
          .and()
          .httpBasic();
    }
  }
}
