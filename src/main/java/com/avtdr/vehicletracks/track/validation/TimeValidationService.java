package com.avtdr.vehicletracks.track.validation;


import com.avtdr.vehicletracks.exception.TimeValidationException;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class TimeValidationService {

    public void checkStartTimeIsBeforeEndTime(ZonedDateTime rangeStart, ZonedDateTime rangeEnd) {
        if (rangeStart != null && rangeEnd != null && (rangeEnd.isBefore(rangeStart))) {
            throw new TimeValidationException("Параметр дата и время начала выборки должен быть раньше даты и времени конца выборки");
        }
    }
}