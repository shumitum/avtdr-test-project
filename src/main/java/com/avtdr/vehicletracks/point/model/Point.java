package com.avtdr.vehicletracks.point.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "video_id")
    @Schema(description = "ID видео", example = "1687154445296")
    private Long videoId;

    @Column(name = "location", columnDefinition = "geometry(Point,4326)")
    private org.locationtech.jts.geom.Point location;

    @NotNull
    @Column(name = "bearing")
    @Schema(description = "Угол направления движения в градусах", example = "150.83277893066406")
    private Double bearing;

    @NotNull
    @Column(name = "velocity")
    @Schema(description = "Скорость транспортного средства м/c", example = "8.058734893798828")
    private Double velocity;

    @NotNull
    @Column(name = "point_datetime")
    @Schema(description = "Время проезда точки", example = "2023-06-19 06:55:20Z")
    private ZonedDateTime pointDateTime;
}
