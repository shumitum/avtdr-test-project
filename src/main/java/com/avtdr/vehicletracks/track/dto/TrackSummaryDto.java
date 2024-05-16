package com.avtdr.vehicletracks.track.dto;

import lombok.Data;

@Data
public class TrackSummaryDto {
    private Long trackId;
    private Long videoId;
    private Long duration;
    private Double avgVelocity;
    private Double distance;
}
