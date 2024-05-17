package com.avtdr.vehicletracks.geo.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GeometryType {
    LINE_STRING("LineString"),
    POINT("Point");
    public final String type;
}
