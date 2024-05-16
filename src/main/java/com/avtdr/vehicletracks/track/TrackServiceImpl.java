package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.device.DeviceService;
import com.avtdr.vehicletracks.point.PointMapper;
import com.avtdr.vehicletracks.point.PointRepository;
import com.avtdr.vehicletracks.point.dto.MaxVelocityPointDto;
import com.avtdr.vehicletracks.point.model.Point;
import com.avtdr.vehicletracks.track.dto.TrackSummaryDto;
import com.avtdr.vehicletracks.track.model.Track;
import com.avtdr.vehicletracks.utils.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {
    private final TrackRepository trackRepository;
    private final PointRepository pointRepository;
    private final DeviceService deviceService;
    private final PointMapper pointMapper;
    private final TrackMapper trackMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Point> getTrackPoints(String deviceId, ZonedDateTime rangeStart, ZonedDateTime rangeEnd, int from, int size) {
        deviceService.checkDeviceExistence(deviceId);
        return pointRepository.getTrackPoints(deviceId, rangeStart, rangeEnd, PageParam.of(from, size));
    }

    @Override
    @Transactional(readOnly = true)
    public MaxVelocityPointDto getMaxVelocityPoint(String deviceId) {
        deviceService.checkDeviceExistence(deviceId);
        final Point point = pointRepository.getMaxVelocityPointByDeviceId(deviceId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format("Данные по скорости для устройства с deviceID=%s отсутствуют", deviceId)));
        return pointMapper.toMaxVelocityPointDto(point);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrackSummaryDto> getAllTracks() {
        List<Track> tracks = trackRepository.findAll();

        List<TrackSummaryDto> trackSummaryList = tracks.stream()
                .map(trackMapper::toTrackSummaryDto)
                .parallel()
                .toList();

        for (int i = 0; i < trackSummaryList.size(); i++) {
            List<Point> points = tracks.get(i).getPoints();
            if (points.size() > 2) {
                trackSummaryList.get(i).setDuration(calculateDuration(points));
                trackSummaryList.get(i).setDistance(calculateTrackDistance(points));
                trackSummaryList.get(i).setAvgVelocity(calculateAvfVelocity(trackSummaryList.get(i)));
            }
        }

        return trackSummaryList;
    }

    private Double calculateAvfVelocity(TrackSummaryDto track) {
        return track.getDuration() != 0 ? track.getDistance() / track.getDuration() : 0.0;
    }

    private Long calculateDuration(List<Point> points) {
        ZonedDateTime firstPointTime = points.get(0).getPointDateTime();
        ZonedDateTime lastPointTime = points.get(points.size() - 1).getPointDateTime();
        return Duration.between(firstPointTime, lastPointTime).toSeconds();
    }

    private Double calculateTrackDistance(List<Point> points) {
        if (points.size() < 2) {
            return 0.0;
        }
        Double distance = 0.0;
        for (int i = 1; i < points.size(); i++) {
            Point pointFrom = points.get(i - 1);
            Point pointTo = points.get(i);
            Double distanceBetweenPoints = calculateDurationByTwoPoints(pointFrom.getLat(), pointFrom.getLon(),
                    pointTo.getLat(), pointTo.getLon());
            distance += distanceBetweenPoints;
        }
        return distance;
    }

    private Double calculateDurationByTwoPoints(Double lat1, Double lon1, Double lat2, Double lon2) {
        double distance;
        double radLat1;
        double radLat2;
        double theta;
        double radTheta;
        radLat1 = Math.PI * lat1 / 180.0;
        radLat2 = Math.PI * lat2 / 180.0;
        theta = lon1 - lon2;
        radTheta = Math.PI * theta / 180;
        distance = Math.sin(radLat1) * Math.sin(radLat2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radTheta);
        if (distance > 1) {
            distance = 1;
        }
        distance = Math.acos(distance);
        distance = (distance * 180 / Math.PI);
        distance = distance * 60 * 1852.4;
        return distance;
    }
}