package com.avtdr.vehicletracks.geo.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GeoJsonType {
    FEATURE_COLLECTION("FeatureCollection");
    public final String type;
}
