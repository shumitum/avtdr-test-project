package com.avtdr.vehicletracks.geo;

import com.avtdr.vehicletracks.geo.geojson.*;
import com.avtdr.vehicletracks.track.TrackRepository;
import com.avtdr.vehicletracks.track.model.Track;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoServiceImpl implements GeoService {
    private final TrackRepository trackRepository;

    @Override
    @Transactional(readOnly = true)
    public GeoJson getAlltracksGeoJson() {
        List<Track> tracks = trackRepository.findAll();
        if (tracks.isEmpty()) {
            throw new NoSuchElementException("Треки движения транспортных средств отсутствуют в БД");
        }
        List<Feature> features = new ArrayList<>(tracks.size());

        for (Track track : tracks) {
            final Geometry trackGeometry = getTrackGeometry(track);
            Feature trackFeature = Feature.builder()
                    .type(FeatureType.FEATURE.type)
                    .properties(Map.of("TrackID", track.getTrackId().toString(),
                            "VideoID", track.getVideoId().toString()))
                    .geometry(trackGeometry)
                    .build();

            features.add(trackFeature);
        }

        return GeoJson.builder()
                .type(GeoJsonType.FEATURE_COLLECTION.type)
                .features(features)
                .build();
    }

    private List<List<Double>> getTackCoordinates(Track track) {
        return track.getPoints().stream()
                .map(point -> List.of(point.getLon(), point.getLat()))
                .toList();
    }

    private Geometry getTrackGeometry(Track track) {
        List<List<Double>> coordinates = getTackCoordinates(track);

        return Geometry.builder()
                .type(GeometryType.LINE_STRING.type)
                .coordinates(coordinates)
                .build();
    }
}
