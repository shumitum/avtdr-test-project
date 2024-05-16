package com.avtdr.vehicletracks.point;

import com.avtdr.vehicletracks.point.model.Point;
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
    Optional<Point> getMaxVelocityPointByDeviceId(@Param("deviceId")String deviceId);

/*    @Query("select p from Point as p " +
            "where ((:users) is NULL or e.initiator.id in :users) " +
            //"and ((:states) is NULL or e.state in :states) " +
            //"and ((:states) is NULL or e.state in :states) " +
            //"and ((:categories) is NULL or e.category.id in :categories) " +
            "and (cast(:rangeStart as java.time.LocalDateTime) is NULL or p.pointDateTime > :rangeStart) " +
            "and (cast(:rangeEnd as java.time.LocalDateTime) is NULL or p.pointDateTime < :rangeEnd)")
    List<Point> getTrackPoints(@Param("deviceId") String deviceId,
                               @Param("rangeStart") LocalDateTime rangeStart,
                               @Param("rangeEnd") LocalDateTime rangeEnd,
                               PageRequest page);*/
}
