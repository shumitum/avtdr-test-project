package com.avtdr.vehicletracks.track.validation;

import java.time.ZonedDateTime;

public interface TimeValidationService {
    void checkStartTimeIsBeforeEndTime(ZonedDateTime rangeStart, ZonedDateTime rangeEnd);
}
