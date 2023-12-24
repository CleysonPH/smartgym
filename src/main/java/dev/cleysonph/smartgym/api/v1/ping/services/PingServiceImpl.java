package dev.cleysonph.smartgym.api.v1.ping.services;

import org.springframework.stereotype.Service;

import dev.cleysonph.smartgym.api.v1.common.dtos.MessageResponse;

@Service
public class PingServiceImpl implements PingService {

    @Override
    public MessageResponse ping() {
        return new MessageResponse("pong");
    }
    
}
