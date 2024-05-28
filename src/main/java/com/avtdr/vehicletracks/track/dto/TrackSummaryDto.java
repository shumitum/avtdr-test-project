package com.avtdr.vehicletracks.track.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TrackSummaryDto {
    @Schema(description = "ID трека", example = "1")
    private Long trackId;

    @Schema(description = "ID видео", example = "1687154445296")
    private Long videoId;

    @Schema(description = "длительность трека в секундах", example = "567")
    private Long duration;

    @Schema(description = "средняя скорость м/c", example = "30,26")
    private Double avgVelocity;

    @Schema(description = "пройденное расстояние в метрах", example = "4769.000418858445")
    private Double distance; //float
}
