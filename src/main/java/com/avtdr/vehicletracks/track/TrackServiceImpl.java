package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.device.DeviceRepository;
import com.avtdr.vehicletracks.model.Device;
import com.avtdr.vehicletracks.model.Point;
import com.avtdr.vehicletracks.model.Track;
import com.avtdr.vehicletracks.point.PointRepository;
import com.avtdr.vehicletracks.utilities.PageParam;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {
    private final TrackRepository trackRepository;
    private final PointRepository pointRepository;
    private final DeviceRepository deviceRepository;

    @Override
    @Transactional
    public List<Point> getTrackPoints(String deviceId, ZonedDateTime rangeStart, ZonedDateTime rangeEnd, int from, int size) {
        return pointRepository.getTrackPoints(deviceId, rangeStart, rangeEnd, PageParam.of(from, size));
    }

    @Override
    @Transactional
    public Point getMaxVelocityPoint(String deviceId) {
/*        return pointRepository.findMaxVelocityPoint(deviceId)
                .orElseThrow(() -> new NoSuchElementException("Нет точек с таким deviceID"));*/
        return null;
    }

    @Override
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
