package by.bsu.uir.university.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilBeansConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

}
