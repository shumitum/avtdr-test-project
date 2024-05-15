package com.avtdr.vehicletracks.track.validation;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeValidationService {

    public void checkStartTimeIsBeforeEndTime(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        if (rangeStart != null && rangeEnd != null && (rangeEnd.isBefore(rangeStart))) {
            throw new IllegalArgumentException("Параметр дата и время начала выборки должен быть раньше даты и времени конца");
        }
    }
}