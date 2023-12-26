package dev.cleysonph.smartgym.api.v1.ping.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.cleysonph.smartgym.api.v1.common.dtos.MessageResponse;

public class PingServiceImplTest {

    private PingServiceImpl pingService;

    @BeforeEach
    void setUp() {
        pingService = new PingServiceImpl();
    }

    @Test
    void whenPing_thenReturnMessageResponse() {
        var expectedMessageResponse = new MessageResponse("pong");
        var actualMessageResponse = pingService.ping();
        assertEquals(expectedMessageResponse, actualMessageResponse);
    }
    
}
