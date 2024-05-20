package com.avtdr.vehicletracks.track;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@DataJpaTest
@SpringBootTest
@Transactional
@Testcontainers
class TrackRepositoryTest {

    @Autowired
    TrackRepository trackRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:12-alpine");

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
    }
}