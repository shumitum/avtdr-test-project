package com.avtdr.vehicletracks.geo;

import com.avtdr.vehicletracks.geo.geojson.GeoJson;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GeoServiceImplIntegrationTest {

    /**
     * Не забывать запускать докер перед тестами
     */
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:12-alpine");

    private final GeoService geoService;

    @Test
    void getAllTracksGeoJson_whenInvoke_thenReturnGeoJsonObject() {
        GeoJson alltracksGeoJson = geoService.getAlltracksGeoJson();

        assertEquals("FeatureCollection", alltracksGeoJson.getType());
        assertEquals(16, alltracksGeoJson.getFeatures().size());
        assertEquals("Feature", alltracksGeoJson.getFeatures().get(0).getType());
        assertEquals(2, alltracksGeoJson.getFeatures().get(0).getProperties().size());
        assertEquals("LineString", alltracksGeoJson.getFeatures().get(0).getGeometry().getType());
    }
}