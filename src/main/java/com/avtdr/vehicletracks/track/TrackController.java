package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.point.dto.MaxVelocityPointDto;
import com.avtdr.vehicletracks.point.model.Point;
import com.avtdr.vehicletracks.track.dto.TrackSummaryDto;
import com.avtdr.vehicletracks.track.validation.TimeValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "ТРЕКИ ТРАНСПОРТНЫХ СРЕДСТВ", description = "API для работы с треками движения транспортных средств")
public class TrackController {
    private final TrackService trackService;
    private final TimeValidationService timeValidationService;

    @GetMapping("/points")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Запрос на получение списка точек",
            description = "Эндпоинт принимает ID устройства, дату и время начала и конца выборки " +
                    "(в формате - 2023-06-19T06:01:00Z) и параметры пагинации (номер страницы и кол-во элементов на ней.")
    public List<Point> getTrackPoints(@Parameter(description = "ID устройства", example = "32e59c906a958812")
                                      @RequestParam(name = "deviceId", required = false) String deviceId,
                                      @Parameter(description = "Дата и время начала выборки", example = "2023-06-19T06:01:00Z")
                                      @RequestParam(name = "rangeStart", required = false) ZonedDateTime rangeStart,
                                      @Parameter(description = "Дату и время конца выборки", example = "2023-06-19T06:02:00Z")
                                      @RequestParam(name = "rangeEnd", required = false) ZonedDateTime rangeEnd,
                                      @Parameter(description = "Номер страницы")
                                      @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                      @Parameter(description = "Количество элементов на странице")
                                      @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        log.info("Запрос на получение списка точек с параметрами deviceId={}, rangeStart={}, rangeEnd={}, from={}, size={} ",
                deviceId, rangeStart, rangeEnd, from, size);
        timeValidationService.checkStartTimeIsBeforeEndTime(rangeStart, rangeEnd);
        return trackService.getTrackPoints(deviceId, rangeStart, rangeEnd, from, size);
    }

    @GetMapping("/device/{deviceId}/max-velocity-point")
    @ResponseStatus(HttpStatus.OK)
    public MaxVelocityPointDto getMaxVelocityPoint(@Parameter(description = "ID устройства", example = "32e59c906a958812")
                                                   @PathVariable("deviceId") String deviceId) {
        log.info("Запрос на получение точки с максимальной скоростью устройства с ID={}", deviceId);
        return trackService.getMaxVelocityPoint(deviceId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<TrackSummaryDto> getAllTracks() {
        log.info("Запрос на получение списка треков");
        return trackService.getAllTracks();
    }
}
