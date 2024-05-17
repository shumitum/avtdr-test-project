package com.avtdr.vehicletracks.track;

import com.avtdr.vehicletracks.track.validation.TimeValidationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TrackController.class)
class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrackService trackService;

    @MockBean
    private TimeValidationService timeValidationService;

    @Test
    @SneakyThrows
    void getTrackPoints_whenInvokedWithCorrectParams_thenReturnOkStatus() {
        mockMvc.perform(get("/tracks/points")
                        .param("deviceId", "32e59c906a958812")
                        .param("rangeStart", "2023-06-19T06:01:00Z")
                        .param("rangeEnd", "2023-06-19T06:02:00Z")
                        .param("from", "0")
                        .param("size", "10")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(trackService, times(1))
                .getTrackPoints("32e59c906a958812",
                        ZonedDateTime.of(LocalDateTime.of(2023, 6, 19, 6, 1),
                                ZoneId.of("Z")),
                        ZonedDateTime.of(LocalDateTime.of(2023, 6, 19, 6, 2),
                                ZoneId.of("Z")),
                        0, 10);

        verify(timeValidationService, times(1))
                .checkStartTimeIsBeforeEndTime(
                        ZonedDateTime.of(LocalDateTime.of(2023, 6, 19, 6, 1),
                                ZoneId.of("Z")),
                        ZonedDateTime.of(LocalDateTime.of(2023, 6, 19, 6, 2),
                                ZoneId.of("Z")));
    }

    @Test
    @SneakyThrows
    void getTrackPoints_whenInvokedWithInCorrectParams_thenReturnBadRequestStatus() {
        mockMvc.perform(get("/tracks/points")
                        .param("deviceId", "32e59c906a958812")
                        .param("rangeStart", "2023-06-19T06:02:00Z")
                        .param("rangeEnd", "2023-06-19T06:01:00Z")
                        .param("from", "-10")
                        .param("size", "10")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());

        verify(trackService, times(0))
                .getTrackPoints("32e59c906a958812",
                        ZonedDateTime.of(LocalDateTime.of(2023, 6, 19, 6, 1),
                                ZoneId.of("Z")),
                        ZonedDateTime.of(LocalDateTime.of(2023, 6, 19, 6, 2),
                                ZoneId.of("Z")),
                        -10, 10);
    }

    @Test
    @SneakyThrows
    void getMaxVelocityPoint_whenInvokedWithCorrectParams_thenReturnOkStatus() {
        mockMvc.perform(get("/tracks/device/{deviceId}/max-velocity-point", "32e59c906a958812")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(trackService, times(1))
                .getMaxVelocityPoint("32e59c906a958812");
    }

    @Test
    @SneakyThrows
    void getAllTracks_whenInvokedWithCorrectParams_thenReturnOkStatus() {
        mockMvc.perform(get("/tracks/all")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(trackService, times(1))
                .getAllTracks();
    }
}