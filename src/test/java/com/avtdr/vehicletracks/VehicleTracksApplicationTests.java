package com.avtdr.vehicletracks;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Testcontainers
class VehicleTracksApplicationTests {

    /**
     * Не забывать запускать докер перед тестами
     */
    static DockerImageName postgisImage = DockerImageName.parse("postgis/postgis:15-3.4-alpine")
            .asCompatibleSubstituteFor("postgres");

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgis = new PostgreSQLContainer<>(postgisImage);

    @Test
    void connectionEstablished() {
        assertThat(postgis.isCreated()).isTrue();
        assertThat(postgis.isRunning()).isTrue();
    }

    @Test
    void contextLoads() {
    }

}
