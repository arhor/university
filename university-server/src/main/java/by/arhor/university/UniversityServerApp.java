package by.arhor.university;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@EnableCaching
@SpringBootApplication
public class UniversityServerApp {

  public static void main(String[] args) {
    SpringApplication.run(UniversityServerApp.class, args);
  }

  @Bean
  public CommandLineRunner run(RestTemplate restTemplate) {
    return args -> {
      log.info("University-app started");
      if (args != null && args.length > 0) {
        log.info("Passed arguments: {}", (Object) args);
      }
    };
  }
}
