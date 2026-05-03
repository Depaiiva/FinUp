package br.com.finup.core.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

  @Bean
  public Flyway flyway(DataSource dataSource) {
    Flyway flyway = Flyway.configure()
        .baselineOnMigrate(true)
        .dataSource(dataSource)
        .locations("classpath:db/migration")
        .load();

    flyway.migrate();

    return flyway;
  }
}
