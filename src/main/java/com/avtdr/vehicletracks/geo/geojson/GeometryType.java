package com.avtdr.vehicletracks.geo.geojson;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GeometryType {
    LINE_STRING("LineString"),
    POINT("Point");
    private final String type;
}
