package com.avtdr.vehicletracks.geo.geojson;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Geometry {
    private List<List<Double>> coordinates;
    private String type;
}
