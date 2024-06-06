package com.avtdr.vehicletracks.geo;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GeoController.class)
class GeoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeoService geoService;

    @Test
    @SneakyThrows
    void getAllTracksGeoJson_whenInvokedWithCorrectParams_thenReturnOkStatus() {
        mockMvc.perform(get("/geo/tracks/all")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        verify(geoService, times(1))
                .getAllTracksGeoJson();
    }
}