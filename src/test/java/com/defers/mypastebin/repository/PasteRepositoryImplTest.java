package com.defers.mypastebin.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

//@DataR2dbcTest
@SpringBootTest
@Testcontainers
@Slf4j
@ActiveProfiles("test")
class PasteRepositoryImplTest {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("testDB")
            .withPassword("inmemory")
            .withUsername("inmemory");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://"
                + postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort()
                + "/" + postgreSQLContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.r2dbc.password", () -> postgreSQLContainer.getPassword());
    }

    @Autowired
    DatabaseClient client;

    @Autowired
    PasteRepositoryImpl pasteRepository;

    @Test
    void findAll() {
        StepVerifier.create(pasteRepository.findAll())
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }
}