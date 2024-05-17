package com.avtdr.vehicletracks.track.model;

import com.avtdr.vehicletracks.device.model.Device;
import com.avtdr.vehicletracks.point.model.Point;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "track", schema = "public")
public class Track {
    @Id
    @Column(name = "track_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackId;

    @NotNull
    @Column(name = "video_id", unique = true)
    private Long videoId;

    @NotNull
    @Column(name = "video_creation_date")
    private ZonedDateTime videoCreationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", referencedColumnName = "device_id")
    @ToString.Exclude
    private Device device;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", referencedColumnName = "video_id")
    @ToString.Exclude
    private List<Point> points;
}