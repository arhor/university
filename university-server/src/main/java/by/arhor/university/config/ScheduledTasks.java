package by.arhor.university.config;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ScheduledTasks {

  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

  @CacheEvict(allEntries = true, value = {"users"})
  @Scheduled(fixedDelay = 5 * 60 * 1000 ,  initialDelay = 500)
  public void reportCacheEvict() {
    log.info("Flush Cache {}", new Date());
  }

}
