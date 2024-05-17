package com.avtdr.vehicletracks.point.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "point", schema = "public")
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Point {
    @Id
    @Column(name = "point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID точки", example = "1")
    private Long pointId;

    @Column(name = "video_id")
    @Schema(description = "ID видео", example = "1687154445296")
    private Long videoId;

    @Column(name = "lat")
    @Schema(description = "Широта в градусах", example = "55.8704392")
    private Double lat;

    @Column(name = "lon")
    @Schema(description = "Долгота в градусах", example = "49.2257039")
    private Double lon;

    @Column(name = "bearing")
    @Schema(description = "Угол направления движения в градусах", example = "150.83277893066406")
    private Double bearing;

    @Column(name = "velocity")
    @Schema(description = "Скорость транспортного средства м/c", example = "8.058734893798828")
    private Double velocity;

    @Column(name = "point_datetime")
    @Schema(description = "Время проезда точки", example = "2023-06-19 06:55:20Z")
    private ZonedDateTime pointDateTime;
}
