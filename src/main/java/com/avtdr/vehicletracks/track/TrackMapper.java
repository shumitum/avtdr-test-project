package com.avtdr.vehicletracks.track;


import com.avtdr.vehicletracks.track.dto.TrackSummaryDto;
import com.avtdr.vehicletracks.track.model.Track;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface TrackMapper {
    TrackSummaryDto toTrackSummaryDto(Track track);
}

//    @Mapping(target = "author", source = "comment.author")
//    @Mapping(target = "eventId", source = "comment.event.id")
//    List<ViewCommentByAdminDto> toViewCommentByAdminDtoList(List<Comment> comments);