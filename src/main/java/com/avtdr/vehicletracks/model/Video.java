package com.avtdr.vehicletracks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @NotNull
    @Column(name = "video_id")
    private Long videoId;

    @NotNull
    @Column(name = "video_creation_date")
    private ZonedDateTime videoCreationDate;
}
