package com.avtdr.vehicletracks.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@RequiredArgsConstructor
@Table(name = "devices", schema = "public")
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Device {
    @Id
    @Column(name = "device_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID deviceId;

    @NotNull
    @Builder.Default
    @Column(name = "import_date")
    private ZonedDateTime importDate = ZonedDateTime.now();

    @Column(name = "description")
    private String description;
}
