package com.codfish.bikeSalesAndService.integration.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

@TestConfiguration
public class PersistenceContainerTestConfiguration {

    private static final String POSTGRESQL_CONTAINER = "postgres:17.0";

    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgresqlContainer() {
        return new PostgreSQLContainer<>(POSTGRESQL_CONTAINER)
            .withUsername("test")
            .withPassword("test")
            .withStartupTimeout(Duration.ofMinutes(2))
            .waitingFor(Wait.forListeningPort());
    }
}
