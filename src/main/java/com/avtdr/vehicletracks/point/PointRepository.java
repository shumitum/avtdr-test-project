package com.avtdr.vehicletracks.point;

import com.avtdr.vehicletracks.point.dto.MaxVelocityPointDto;
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
    List<Point> findTrackPoints(@Param("deviceId") String deviceId,
                                @Param("rangeStart") ZonedDateTime rangeStart,
                                @Param("rangeEnd") ZonedDateTime rangeEnd,
                                PageRequest page);

    @Query("select new com.avtdr.vehicletracks.point.dto.MaxVelocityPointDto(p.pointId, p.location, p.velocity, p.pointDateTime) " +
            "from Point as p " +
            "where (p.velocity = (select max(p.velocity) from Point as p " +
            "left join Track as t on p.videoId = t.videoId " +
            "where (t.device.deviceId = :deviceId)))")
    Optional<MaxVelocityPointDto> findMaxVelocityPointByDeviceId(@Param("deviceId") String deviceId);

    @Query(value = "select track_id as trackId, p.video_id as videoId, " +
            "EXTRACT(epoch from (MAX(point_datetime) - MIN(point_datetime))) as duration, " +
            "avg(velocity) as avgVelocity, " +
            "ST_LengthSpheroid(ST_MakeLine(p.location order by p.point_id), 'SPHEROID[\"WGS 84\",6378137,298.257223563]') as distance " +
            "from point as p inner join track as t on p.video_id = t.video_id " +
            "group by p.video_id, t.track_id " +
            "order by t.track_id",
            nativeQuery = true)
    List<TrackSummary> findAllTrackSummary();

    @Query(value = "select json_build_object('type', 'FeatureCollection', 'features', json_agg(ST_AsGeoJSON(g.*)\\:\\:json)) " +
            "from (select t.track_id, p.video_id, ST_MakeLine(p.location) " +
            "from point as p inner join track as t on p.video_id = t.video_id " +
            "group by p.video_id, t.track_id " +
            "order by track_id) as g",
            nativeQuery = true)
    String findTracksJsonRepresentation();

    @Query(value = "select * from point " +
            "where ST_DWithin(location\\:\\:geography, :point\\:\\:geography, :radius, true) " +
            "order by st_distance(location\\:\\:geography, :point\\:\\:geography, true)",
            nativeQuery = true)
    List<Point> findPointsWithinRadius(@Param("point") org.locationtech.jts.geom.Point point,
                                       @Param("radius") Double radius);
}