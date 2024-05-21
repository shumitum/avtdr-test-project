package com.avtdr.vehicletracks.geo.geojson;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GeoJsonType {
    FEATURE_COLLECTION("FeatureCollection");
    private final String type;
}
