package dev.cleysonph.smartgym.api.v1.ping.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cleysonph.smartgym.api.v1.common.dtos.MessageResponse;
import dev.cleysonph.smartgym.api.v1.ping.services.PingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ping")
public class PingRestController {

    private final PingService pingService;

    @GetMapping
    public MessageResponse ping() {
        return pingService.ping();
    }
    
}
