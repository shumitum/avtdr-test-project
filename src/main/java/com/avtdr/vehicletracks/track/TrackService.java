package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.model.Point;

import java.time.LocalDateTime;

public interface TrackService {
    void getTrackPoints(String deviceId, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    Point getMaxVelocityPoint(String deviceId);

    void getAllTracks();

    void test();

    void tryy();
}
