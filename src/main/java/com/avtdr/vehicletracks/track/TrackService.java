package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.point.dto.MaxVelocityPointDto;
import com.avtdr.vehicletracks.point.model.Point;
import com.avtdr.vehicletracks.track.dto.TrackSummaryDto;
import com.avtdr.vehicletracks.track.dto.TrackSummary;

import java.time.ZonedDateTime;
import java.util.List;

public interface TrackService {
    List<Point> getTrackPoints(String deviceId, ZonedDateTime rangeStart, ZonedDateTime rangeEnd, int from, int size);

    MaxVelocityPointDto getMaxVelocityPoint(String deviceId);

    List<TrackSummaryDto> getAllTracks();

    List<TrackSummary> getAllTracksTest();
}
