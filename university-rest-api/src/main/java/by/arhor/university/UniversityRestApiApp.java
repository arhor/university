package by.arhor.university;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@EnableCaching
@EnableOAuth2Client
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class UniversityRestApiApp {

  public static void main(String[] args) {
    SpringApplication.run(UniversityRestApiApp.class, args);
  }

  @Bean
  public CommandLineRunner run(RestTemplate restTemplate) {
    return args -> log.info("University-app started");
  }
}
