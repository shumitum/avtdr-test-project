package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.point.dto.MaxVelocityPointDto;
import com.avtdr.vehicletracks.point.model.Point;
import com.avtdr.vehicletracks.track.dto.TrackSummaryDto;
import com.avtdr.vehicletracks.track.validation.TimeValidationService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/tracks")
public class TrackController {
    private final TrackService trackService;
    private final TimeValidationService timeValidationService;

    @GetMapping("/points")
    @ResponseStatus(HttpStatus.OK)
    public List<Point> getTrackPoints(@RequestParam(required = false) String deviceId,
                                      @RequestParam(required = false) ZonedDateTime rangeStart,
                                      @RequestParam(required = false) ZonedDateTime rangeEnd,
                                      @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                      @RequestParam(defaultValue = "10") @Positive int size) {
        log.info("Запрос на получение списка точек с параметрами deviceId={}, rangeStart={}, rangeEnd={}, from={}, size={} ",
                deviceId, rangeStart, rangeEnd, from, size);
        timeValidationService.checkStartTimeIsBeforeEndTime(rangeStart, rangeEnd);
        return trackService.getTrackPoints(deviceId, rangeStart, rangeEnd, from, size);
    }

    @GetMapping("/device/{deviceId}/max-velocity-point")
    @ResponseStatus(HttpStatus.OK)
    public MaxVelocityPointDto getMaxVelocityPoint(@PathVariable String deviceId) {
        log.info("Запрос на получение точки с максимальной скоростью устройства с ID={}", deviceId);
        return trackService.getMaxVelocityPoint(deviceId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TrackSummaryDto> getAllTracks() {
        log.info("Запрос на получение списка треков");
        return trackService.getAllTracks();
    }

    //@GetMapping("/test")
    //@ResponseStatus(HttpStatus.OK)
    //public void test() {
    //    trackService.test();
    //}
//
    //@GetMapping("/tryy")
    //@ResponseStatus(HttpStatus.OK)
    //public void tryy() {
    //    trackService.tryy();
    //}
}
