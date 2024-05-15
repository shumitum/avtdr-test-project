package com.avtdr.vehicletracks.model;

import jakarta.persistence.*;
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
    @Column(name = "device_id")
    //@GeneratedValue(strategy = GenerationType.UUID)
    private String deviceId;

    @NotNull
    @Builder.Default
    @Column(name = "import_date")
    private ZonedDateTime importDate = ZonedDateTime.now();

    @Column(name = "description")
    private String description;
}
