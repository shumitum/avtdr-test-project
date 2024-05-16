package com.avtdr.vehicletracks.point.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class MaxVelocityPointDto {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ssX";

    private Long pointId;
    private Double lat;
    private Double lon;
    private Double velocity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private ZonedDateTime pointDateTime;
}
