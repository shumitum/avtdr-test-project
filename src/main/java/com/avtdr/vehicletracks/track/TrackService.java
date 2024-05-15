package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.model.Point;
import com.avtdr.vehicletracks.point.MaxVelocityPointDto;

import java.time.ZonedDateTime;
import java.util.List;

public interface TrackService {
    List<Point> getTrackPoints(String deviceId, ZonedDateTime rangeStart, ZonedDateTime rangeEnd, int from, int size);

    MaxVelocityPointDto getMaxVelocityPoint(String deviceId);

    void getAllTracks();

    void test();

    void tryy();
}
