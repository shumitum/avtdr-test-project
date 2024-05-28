package com.avtdr.vehicletracks.point;

import com.avtdr.vehicletracks.point.model.Point;
import com.avtdr.vehicletracks.track.dto.TrackSummary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

    @Query("select p from Point as p left join Track as t on p.videoId = t.videoId " +
            "where ((:deviceId) is NULL or t.device.deviceId = :deviceId) " +
            "and (cast(:rangeStart as java.time.ZonedDateTime) is NULL or p.pointDateTime > :rangeStart) " +
            "and (cast(:rangeEnd as java.time.ZonedDateTime) is NULL or p.pointDateTime < :rangeEnd)")
    List<Point> getTrackPoints(@Param("deviceId") String deviceId,
                               @Param("rangeStart") ZonedDateTime rangeStart,
                               @Param("rangeEnd") ZonedDateTime rangeEnd,
                               PageRequest page);

    @Query("select p from Point as p " +
            "where (p.velocity = (select max(p.velocity) from Point as p " +
            "left join Track as t on p.videoId = t.videoId " +
            "where (t.device.deviceId = :deviceId)))")
    Optional<Point> getMaxVelocityPointByDeviceId(@Param("deviceId") String deviceId);

    @Query(value = "SELECT EXTRACT(epoch FROM (SELECT MAX(point_datetime) - MIN(point_datetime)" +
            " FROM point where video_id = ?1))",
            nativeQuery = true)
    Long getTrackDurationByVideoId(Long videoId);

    @Query(value = "SELECT track_id, p.video_id, " +
            "EXTRACT(epoch FROM (MAX(point_datetime) - MIN(point_datetime))) as duration, " +
            "avg(velocity) as avgVelocity, " +
            "ST_LengthSpheroid(ST_MakeLine(p.location ORDER BY point_id), 'SPHEROID[\"WGS 84\",6378137,298.257223563]') as distance " +
            "FROM point as p inner join track as t on p.video_id = t.video_id " +
            "group by p.video_id, track_id " +
            "order by track_id",
            nativeQuery = true)
    List<TrackSummary> findAllTrackSummary();
}

