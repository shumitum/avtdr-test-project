package com.avtdr.vehicletracks.point;

import com.avtdr.vehicletracks.model.Point;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointMapper {
    MaxVelocityPointDto toMaxVelocityPointDto(Point point);
}