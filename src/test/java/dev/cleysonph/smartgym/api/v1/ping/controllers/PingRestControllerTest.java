package dev.cleysonph.smartgym.api.v1.ping.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import dev.cleysonph.smartgym.api.v1.common.dtos.MessageResponse;
import dev.cleysonph.smartgym.api.v1.ping.services.PingService;

@WebMvcTest(PingRestController.class)
class PingRestControllerTest {

    @MockBean
    private PingService pingService;

    @Autowired
    private MockMvc mockMvc;

    private static final String PING_ENDPOINT = "/api/v1/ping";

    @Test
    void whenGETPing_thenReturnMessageResponse_200() throws Exception {
        var body = new MessageResponse("ping");

        when(pingService.ping()).thenReturn(body);

        mockMvc.perform(get(PING_ENDPOINT).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(body.getMessage()));
    }
    
}
