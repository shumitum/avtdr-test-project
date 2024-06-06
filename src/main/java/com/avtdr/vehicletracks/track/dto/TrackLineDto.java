package com.avtdr.vehicletracks.track.dto;

import org.locationtech.jts.geom.LineString;

public interface TrackLineDto {
    Long getTrackId();

    Long getVideoId();

    LineString getTrackLine();
}
