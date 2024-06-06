package com.avtdr.vehicletracks.geo;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GeoServiceImplIntegrationTest {

    /**
     * Не забывать запускать докер перед тестами
     */
    static DockerImageName postgisImage = DockerImageName.parse("postgis/postgis:15-3.4-alpine")
            .asCompatibleSubstituteFor("postgres");

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgis = new PostgreSQLContainer<>(postgisImage);

    private final GeoService geoService;

    @Test
    void getAllTracksGeoJson_whenInvoke_thenReturnGeoJsonObject() {
        String alltracksGeoJson = geoService.getAllTracksGeoJson();

        assertFalse(alltracksGeoJson.isBlank());
        assertTrue(alltracksGeoJson.contains("FeatureCollection"));
        assertTrue(alltracksGeoJson.contains("LineString"));
    }
}