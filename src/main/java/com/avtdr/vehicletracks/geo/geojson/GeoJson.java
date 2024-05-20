package com.avtdr.vehicletracks.geo.geojson;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoJson {
    private String type;
    private List<Feature> features;
}