package com.avtdr.vehicletracks.point.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class MaxVelocityPointDto {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ssX";

    @Schema(description = "ID точки", example = "1")
    private Long pointId;

    @Schema(description = "Координаты точки lon, lat", example = "49.2257039, 55.8704392")
    private Point location;

    @Schema(description = "Скорость транспортного средства м/c", example = "8.058734893798828")
    private Double velocity;

    @Schema(description = "Время проезда точки", example = "2023-06-19 06:55:20Z")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private ZonedDateTime pointDateTime;
}
