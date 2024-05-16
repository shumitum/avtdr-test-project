package com.avtdr.vehicletracks.point;

import com.avtdr.vehicletracks.point.dto.MaxVelocityPointDto;
import com.avtdr.vehicletracks.point.model.Point;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointMapper {
    MaxVelocityPointDto toMaxVelocityPointDto(Point point);
}