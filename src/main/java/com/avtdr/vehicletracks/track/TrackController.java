package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.point.dto.MaxVelocityPointDto;
import com.avtdr.vehicletracks.point.model.Point;
import com.avtdr.vehicletracks.track.dto.TrackSummary;
import com.avtdr.vehicletracks.track.validation.TimeValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
            description = "Данный эндпоинт принимает ID устройства, дату и время начала и конца выборки " +
                    "(в формате - 2023-06-19T06:01:00Z) и параметры пагинации (номер страницы и кол-во элементов на ней.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Время начала выборки должны быть раньше времени конца выборки", content = @Content),
            @ApiResponse(responseCode = "404", description = "Устройства с указанным deviceID не существует", content = @Content)
    })
    public List<Point> getTrackPoints(@Parameter(description = "ID устройства", example = "32e59c906a958812")
                                      @RequestParam(name = "deviceId", required = false) String deviceId,
                                      @Parameter(description = "Дата и время начала выборки (ZonedDateTime)", example = "2023-06-19T06:01:00Z")
                                      @RequestParam(name = "rangeStart", required = false) ZonedDateTime rangeStart,
                                      @Parameter(description = "Дату и время конца выборки (ZonedDateTime)", example = "2023-06-19T06:02:00Z")
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
    @Operation(summary = "Запрос точки в которой была достигнута максимальная скорость по ID устройства",
            description = "Данный эндпоинт возвращает точку в которой была достигнута максимальная скорость")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Данные по скорости для устройства с указанным deviceID отсутствуют", content = @Content)
    })
    public MaxVelocityPointDto getMaxVelocityPoint(@Parameter(description = "ID устройства", example = "32e59c906a958812")
                                                   @PathVariable("deviceId") String deviceId) {
        log.info("Запрос на получение точки с максимальной скоростью устройства с ID={}", deviceId);
        return trackService.getMaxVelocityPoint(deviceId);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Запрос данных по каждому треку",
            description = "Данный эндпоинт возвращает список треков, где для каждого трека будет указана длительность" +
                    " трека, средняя скорость и пройденное расстояние")
    public List<TrackSummary> getAllTracks() {
        log.info("Запрос на получение списка треков");
        return trackService.getAllTracks();
    }

    @GetMapping("/points-within-radius")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Поиск точек, находящихся в указанном радиусе от переданной точки",
            description = "Список точек, находящихся в указанном радиусе (в метрах) от точки, с заданной долготой и" +
                    " широтой, и упорядоченных по возрастанию удаленности от неё.")
    public List<Point> getPointsWithinRadius(@Parameter(description = "Долгота точки в градусах", example = "49.1025455")
                                             @RequestParam(name = "lon") @Min(-180) @Max(+180) Double lon,
                                             @Parameter(description = "Широта точки в градусах", example = "55.7964352")
                                             @RequestParam(name = "lat") @Min(-90) @Max(+90) Double lat,
                                             @Parameter(description = "Радиус поиска в метрах", example = "20")
                                             @RequestParam(name = "radius") @Positive Double radius) {
        return trackService.getPointsWithinRadius(lon, lat, radius);
    }
}
