package dev.cleysonph.smartgym.core.service.datetime;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;

@Service
public class DateTimeServiceImpl implements DateTimeService {

    @Override
    public ZonedDateTime utcNow() {
        return ZonedDateTime.now(ZoneOffset.UTC);
    }
    
}
