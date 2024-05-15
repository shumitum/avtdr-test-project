package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.model.Track;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackRepository extends JpaRepository<Track, Long> {

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"device"})
    Optional<Track> findById(Long trackId);

    @Override
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "device")
    Track getReferenceById(Long trackId);
}
