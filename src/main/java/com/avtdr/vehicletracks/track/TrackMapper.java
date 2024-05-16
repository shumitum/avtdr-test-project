package com.avtdr.vehicletracks.track;


import com.avtdr.vehicletracks.track.dto.TrackSummaryDto;
import com.avtdr.vehicletracks.track.model.Track;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackMapper {
    TrackSummaryDto toTrackSummaryDto(Track track);
}