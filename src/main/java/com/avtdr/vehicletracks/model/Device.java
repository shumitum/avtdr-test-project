package com.avtdr.vehicletracks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "device", schema = "public")
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Device {
    @Id
    @Column(name = "device_id", unique = true)
    private String deviceId;

    @NotNull
    @Builder.Default
    @Column(name = "import_date")
    private ZonedDateTime importDate = ZonedDateTime.now();

    @Column(name = "description")
    private String description;
}
