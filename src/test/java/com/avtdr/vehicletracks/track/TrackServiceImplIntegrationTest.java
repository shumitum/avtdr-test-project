package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.point.dto.MaxVelocityPointDto;
import com.avtdr.vehicletracks.point.model.Point;
import com.avtdr.vehicletracks.track.dto.TrackSummary;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Testcontainers
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TrackServiceImplIntegrationTest {

    /**
     * Не забывать запускать докер перед тестами
     */
    static DockerImageName postgisImage = DockerImageName.parse("postgis/postgis:15-3.4-alpine")
            .asCompatibleSubstituteFor("postgres");

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgis = new PostgreSQLContainer<>(postgisImage);

    private final TrackService trackService;

    private final ZonedDateTime rangeStart = ZonedDateTime.of(LocalDateTime.of(2023, 6, 19, 6, 1), ZoneId.of("Z"));
    private final ZonedDateTime rangeEnd = ZonedDateTime.of(LocalDateTime.of(2023, 6, 19, 6, 2), ZoneId.of("Z"));

    @Test
    void getTrackPoints_whenInvokedWithCorrectParameters_thenReturnListOfTrackPoints() {
        String deviceId = "32e59c906a958812";
        List<Point> trackPoints = trackService.getTrackPoints(deviceId, rangeStart, rangeEnd, 0, 100);

        assertEquals(58, trackPoints.size());
    }

    @Test
    void getTrackPoints_whenInvokedWithInCorrectParameters_thenReturnNoSuchElementException() {
        String deviceId = "qwe123";

        assertThrows(NoSuchElementException.class, () ->
                trackService.getTrackPoints(deviceId, rangeStart, rangeEnd, 0, 100));
    }

    @Test
    void getMaxVelocityPoint_whenInvokeWithValidDeviceId_thenReturnMaxVelocityPoint() {
        String deviceId = "5579dd7e3f612732";
        MaxVelocityPointDto maxVelocityPoint = trackService.getMaxVelocityPoint(deviceId);

        assertNotNull(maxVelocityPoint);
        assertEquals(18.4747314453125, maxVelocityPoint.getVelocity());
        assertEquals(6352, maxVelocityPoint.getPointId());
    }

    @Test
    void getMaxVelocityPoint_whenInvokeWithInValidDeviceId_thenReturnNoSuchElementException() {
        String deviceId = "qwe123";

        assertThrows(NoSuchElementException.class, () -> trackService.getMaxVelocityPoint(deviceId));
    }

    @Test
    void getAllTracks_whenInvoke_thenReturnListOfAllTracks() {
        List<TrackSummary> allTracks = trackService.getAllTracks();

        assertEquals(16, allTracks.size());
        assertEquals(567, allTracks.get(0).getDuration());
    }

    @Test
    void getPointsWithinRadius_whenInvoke_thenReturnListOfPoints() {
        List<Point> points = trackService.getPointsWithinRadius(49.1023785, 55.7965796, 20.0);

        assertEquals(18, points.size());
        assertEquals(4557, points.get(0).getPointId());
        assertEquals(4570, points.get(17).getPointId());
    }
}