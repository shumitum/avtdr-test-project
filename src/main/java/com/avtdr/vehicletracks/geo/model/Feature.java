package com.avtdr.vehicletracks.geo.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Feature {
    private String type;
    private Map<String, String> properties;
    private Geometry geometry;
}
