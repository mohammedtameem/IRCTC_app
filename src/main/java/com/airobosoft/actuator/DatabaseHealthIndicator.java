package com.airobosoft.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator
        implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Health health() {

        try {

            // simple query
            jdbcTemplate.queryForObject(
                    "SELECT 1",
                    Integer.class
            );

            return Health.up()
                    .withDetail(
                            "database",
                            "MySQL Database is UP"
                    )
                    .build();

        } catch (Exception e) {

            return Health.down()
                    .withDetail(
                            "database",
                            "MySQL Database is DOWN"
                    )
                    .withDetail(
                            "error",
                            e.getMessage()
                    )
                    .build();
        }
    }
}