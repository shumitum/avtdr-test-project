package com.avtdr.vehicletracks.geo;

import com.avtdr.vehicletracks.point.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoServiceImpl implements GeoService {
    private final PointRepository pointRepository;

    @Override
    @Transactional(readOnly = true)
    public String getAllTracksGeoJson() {
        return pointRepository.findTracksJsonRepresentation();
    }
}
