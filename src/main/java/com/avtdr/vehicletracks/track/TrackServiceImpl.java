package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.device.DeviceRepository;
import com.avtdr.vehicletracks.device.DeviceService;
import com.avtdr.vehicletracks.model.Device;
import com.avtdr.vehicletracks.model.Point;
import com.avtdr.vehicletracks.model.Track;
import com.avtdr.vehicletracks.point.MaxVelocityPointDto;
import com.avtdr.vehicletracks.point.PointMapper;
import com.avtdr.vehicletracks.point.PointRepository;
import com.avtdr.vehicletracks.utilities.PageParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {
    private final TrackRepository trackRepository;
    private final PointRepository pointRepository;
    private final DeviceRepository deviceRepository;
    private final DeviceService deviceService;
    private final PointMapper pointMapper;

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
    public void getAllTracks() {

    }

    @Override
    @Transactional
    public void test() {
        Device device = Device.builder()
                .deviceId("32e59c906a958812")
                .importDate(ZonedDateTime.now())
                .description("dev_1")
                .build();
        Device device1 = deviceRepository.save(device);

        Point point = pointRepository.save(Point.builder()
                .lat(55.8716167)
                .lon(49.2226383)
                .bearing(187.0)
                .velocity(3.4012844562530518)
                .pointDateTime(ZonedDateTime.now())
                .videoId(1687154445296L)
                .build());


        System.out.println(point);
        System.out.println("========================\n");
        System.out.println(device1);
        System.out.println("========================\n");
        Track track = Track.builder()
                .videoId(1687154445296L)
                .videoCreationDate(ZonedDateTime.now())
                .device(device)
                .points(List.of(point))
                .build();
        Track track1 = trackRepository.save(track);
        System.out.println(track1);
        System.out.println("========================\n");
    }

    @Override
    @Transactional
    public void tryy() {
        Track track = trackRepository.getReferenceById(1L);
        System.out.println(track);
        System.out.println("========================\n");
        List<Point> points = track.getPoints();
        System.out.println(points);
    }

}
