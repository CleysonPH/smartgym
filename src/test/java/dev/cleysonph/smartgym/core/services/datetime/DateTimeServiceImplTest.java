package dev.cleysonph.smartgym.core.services.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateTimeServiceImplTest {

    private DateTimeServiceImpl dateTimeServiceImpl;

    @BeforeEach
    void setUp() {
        dateTimeServiceImpl = new DateTimeServiceImpl();
    }

    @Test
    void whenCallUtcNow_thenItShouldReturnTheCurrentUtcDateTime() {
        var utcNow = ZonedDateTime.now(ZoneOffset.UTC);
        var result = dateTimeServiceImpl.utcNow();
        assertEquals(utcNow.getYear(), result.getYear());
        assertEquals(utcNow.getMonth(), result.getMonth());
        assertEquals(utcNow.getDayOfMonth(), result.getDayOfMonth());
        assertEquals(utcNow.getHour(), result.getHour());
        assertEquals(utcNow.getMinute(), result.getMinute());
        assertEquals(utcNow.getSecond(), result.getSecond());
    }
    
}
