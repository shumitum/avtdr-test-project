package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.device.DeviceService;
import com.avtdr.vehicletracks.point.PointRepository;
import com.avtdr.vehicletracks.point.model.Point;
import com.avtdr.vehicletracks.utils.PageParam;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackServiceImplTest {

    @InjectMocks
    private TrackServiceImpl trackService;

    @Mock
    private PointRepository pointRepository;

    @Mock
    private DeviceService deviceService;

    private final ZonedDateTime rangeStart = ZonedDateTime.of(LocalDateTime.of(2023, 6, 19, 6, 1), ZoneId.of("Z"));
    private final ZonedDateTime rangeEnd = ZonedDateTime.of(LocalDateTime.of(2023, 6, 19, 6, 2), ZoneId.of("Z"));

    @Test
    void getTrackPoints_whenInvokeWithValidParameters_thenReturnListOfTrackPoints() {
        when(pointRepository.getTrackPoints("32e59c906a958812", rangeStart, rangeEnd, PageParam.of(0, 10)))
                .thenReturn(Collections.emptyList());

        List<Point> trackPoints = trackService.getTrackPoints("32e59c906a958812", rangeStart, rangeEnd, 0, 10);

        assertEquals(0, trackPoints.size());
    }

    @Test
    void getTrackPoints_whenInvokeWithWrongTimeParameters_thenReturnListOfTrackPoints() {
        doThrow(NoSuchElementException.class).when(deviceService).checkDeviceExistence(any());

        assertThrows(NoSuchElementException.class, () -> trackService.getTrackPoints("32e59c906a958812", rangeStart, rangeEnd, 0, 10));

        verify(pointRepository, times(0))
                .getTrackPoints("32e59c906a958812", rangeStart, rangeEnd, PageParam.of(0, 10));
    }
}