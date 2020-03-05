package by.arhor.university.config;

import static by.arhor.university.Constants.CACHE_LANGS;
import static by.arhor.university.Constants.CACHE_ROLES;
import static by.arhor.university.Constants.CACHE_USERS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTasks {

  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

  /** Scheduled task to clear cache `users` every 5 minutes. */
  @CacheEvict(allEntries = true, value = {CACHE_USERS})
  @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 500)
  public void reportUsersCacheEvict() {
    log.info("Flush `users` cache");
  }

  /** Scheduled task to clear caches `langs` and `roles` every 24 hours. */
  @CacheEvict(allEntries = true, value = {CACHE_LANGS, CACHE_ROLES})
  @Scheduled(fixedDelay = 24 * 60 * 60 * 1000, initialDelay = 500)
  public void reportLangsRolesCacheEvict() {
    log.info("Flush `langs` and `roles` caches");
  }
}
