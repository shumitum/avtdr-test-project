package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.device.DeviceService;
import com.avtdr.vehicletracks.point.PointRepository;
import com.avtdr.vehicletracks.point.dto.MaxVelocityPointDto;
import com.avtdr.vehicletracks.point.model.Point;
import com.avtdr.vehicletracks.track.dto.TrackSummary;
import com.avtdr.vehicletracks.utils.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {
    private final PointRepository pointRepository;
    private final DeviceService deviceService;

    @Override
    @Transactional(readOnly = true)
    public List<Point> getTrackPoints(String deviceId, ZonedDateTime rangeStart, ZonedDateTime rangeEnd, int from, int size) {
        deviceService.checkDeviceExistence(deviceId);
        return pointRepository.findTrackPoints(deviceId, rangeStart, rangeEnd, PageParam.of(from, size));
    }

    @Override
    @Transactional(readOnly = true)
    public MaxVelocityPointDto getMaxVelocityPoint(String deviceId) {
        deviceService.checkDeviceExistence(deviceId);
        return pointRepository.findMaxVelocityPointByDeviceId(deviceId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Данные по скорости для устройства с deviceID=%s отсутствуют", deviceId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrackSummary> getAllTracks() {
        return pointRepository.findAllTrackSummary();
    }

    @Override
    public List<Point> getPointsWithinRadius(Double lon, Double lat, Double radius) {
        GeometryFactory geoFactory = new GeometryFactory(new PrecisionModel(), 4326);
        org.locationtech.jts.geom.Point point = geoFactory.createPoint(new Coordinate(lon, lat));
        return pointRepository.findPointsWithinRadius(point, radius);
    }
}