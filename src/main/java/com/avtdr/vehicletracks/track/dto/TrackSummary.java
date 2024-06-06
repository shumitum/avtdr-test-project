package com.avtdr.vehicletracks.track.dto;

public interface TrackSummary {
    Long getTrackId();

    Long getVideoId();

    Long getDuration();

    Double getAvgVelocity();

    Float getDistance();
}
