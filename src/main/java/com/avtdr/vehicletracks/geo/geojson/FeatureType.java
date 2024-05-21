package com.avtdr.vehicletracks.geo.geojson;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeatureType {
    FEATURE("Feature");
    private final String type;
}
