package com.amigoscode;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public abstract class AbstractTestContainers {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        ).load();
        flyway.migrate();
        System.out.println();
    }

    @Container
    protected static final PostgreSQLContainer<?> postgreSQLContainer=
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("amigoscode-dao-unit-test")
                    .withUsername("amigoscode")
                    .withPassword("password");

    @DynamicPropertySource
    private static void registerDataSourceProperties(DynamicPropertyRegistry registery){
        registery.add(
                "spring.dataSource.url",
                postgreSQLContainer::getJdbcUrl
        );
        registery.add(
                "spring.dataSource.username",
                postgreSQLContainer::getUsername
        );
        registery.add(
                "spring.dataSource.password",
                postgreSQLContainer::getPassword
        );
    }
    private static DataSource getDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();
    }

    protected static JdbcTemplate getJDBCTemplate(){
        return new JdbcTemplate(getDataSource());
    }

    protected static final Faker FAKER = new Faker();
}
