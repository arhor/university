package by.arhor.university.config;

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

  /**
   * Scheduled task to clear cache `users` every 5 minutes.
   */
  @CacheEvict(allEntries = true, value = "cache_users")
  @Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 500)
  public void reportUsersCacheEvict() {
    log.info("Flush `users` cache");
  }

  /**
   * Scheduled task to clear caches `langs` and `roles` every 25 hours.
   */
  @CacheEvict(allEntries = true, value = {"cache_langs", "cache_roles"})
  @Scheduled(fixedDelay = 24 * 60 * 60 * 1000, initialDelay = 500)
  public void reportLangsRolesCacheEvict() {
    log.info("Flush `langs` and `roles` caches");
  }


}
