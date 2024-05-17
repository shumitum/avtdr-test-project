package com.avtdr.vehicletracks.geo;

import com.avtdr.vehicletracks.geo.model.GeoJson;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/geo")
@Tag(name = "GeoJson ПРЕДСТАВЛЕНИЕ", description = "API для работы с треками в формате GeoJson")
public class GeoController {
    private final GeoService geoService;

    @GetMapping("/tracks/all")
    @ResponseStatus(HttpStatus.OK)
    public GeoJson getAlltracksGeoJson() {
        log.info("Запрос всех треков в формате GeoJson");
        return geoService.getAlltracksGeoJson();
    }
}
