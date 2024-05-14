package com.avtdr.vehicletracks.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Builder
@RequiredArgsConstructor
@Table(name = "points", schema = "public")
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Point {
    @Id
    @Column(name = "point_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", referencedColumnName = "video_id")
    @ToString.Exclude
    private Video video;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "bearing")
    private Double bearing;

    @Column(name = "velocity")
    private Double velocity;

    @Column(name = "point_datetime")
    private ZonedDateTime pointDateTime;

}
