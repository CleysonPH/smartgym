package dev.cleysonph.smartgym.core.services.token;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.cleysonph.smartgym.config.JwtConfigProperties;
import dev.cleysonph.smartgym.core.exceptions.TokenException;
import dev.cleysonph.smartgym.core.models.InvalidatedToken;
import dev.cleysonph.smartgym.core.repositories.InvalidatedTokenRepository;
import dev.cleysonph.smartgym.core.services.datetime.DateTimeService;

class JjwtTokenServiceTest {

    private JjwtTokenService jjwtTokenService;
    private DateTimeService dateTimeService;
    private InvalidatedTokenRepository invalidatedTokenRepository;

    private static final String ACCESS_KEY = "c3ed238a9072d0bbfd7cd1c8432dec8426b91168519a6fd2c73545f821cfb382";
    private static final String REFRESH_KEY = "846f23d576098d859941952181838d2597d730a7ca7d79a03fe46164c2c383c1";
    private static final Integer ACCESS_EXPIRATION = 10;
    private static final Integer REFRESH_EXPIRATION = 20;

    private static final UUID SUB = UUID.fromString("c3ed238a-9072-d0bb-fd7c-d1c8432dec84");

    @BeforeEach
    void setUp() {
        dateTimeService = mock(DateTimeService.class);
        invalidatedTokenRepository = mock(InvalidatedTokenRepository.class);
        var jwtConfigProperties = new JwtConfigProperties(ACCESS_KEY, REFRESH_KEY, REFRESH_EXPIRATION, ACCESS_EXPIRATION);
        jjwtTokenService = new JjwtTokenService(dateTimeService, jwtConfigProperties, invalidatedTokenRepository);
    }


    @Test
    void whenGenerateAccessToken_thenReturnToken() {
        // given
        var sub = SUB;

        // when
        var now = ZonedDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneId.of("UTC"));
        when(dateTimeService.utcNow()).thenReturn(now);
        var token = jjwtTokenService.generateAccessToken(sub);

        // then
        assertNotNull(token);
        assertTrue(token.length() > 0);
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void whenGenerateRefreshToken_thenReturnToken() {
        // when
        var now = ZonedDateTime.of(2023, 1, 1, 1, 0, 0, 0, ZoneId.of("UTC"));
        when(dateTimeService.utcNow()).thenReturn(now);
        var token = jjwtTokenService.generateRefreshToken(SUB);

        // then
        assertNotNull(token);
        assertTrue(token.length() > 0);
        assertEquals(3, token.split("\\.").length);
    }

    @Test
    void whenGetSubFromAccessToken_thenReturnSub() {
        // given
        var now = ZonedDateTime.now(ZoneId.of("UTC"));
        when(dateTimeService.utcNow()).thenReturn(now);
        var token = jjwtTokenService.generateAccessToken(SUB);

        // when
        var sub = jjwtTokenService.getSubFromAccessToken(token);

        // then
        assertEquals(SUB, sub);
    }

    @Test
    void whenGetSubFromRefreshToken_thenReturnSub() {
        // given
        var now = ZonedDateTime.now(ZoneId.of("UTC"));
        when(dateTimeService.utcNow()).thenReturn(now);
        var token = jjwtTokenService.generateRefreshToken(SUB);

        // when
        var sub = jjwtTokenService.getSubFromRefreshToken(token);

        // then
        assertEquals(SUB, sub);
    }

    @Test
    void whenGetSubFromAccessToken_thenThrowException() {
        // given
        var now = ZonedDateTime.now(ZoneId.of("UTC"));
        when(dateTimeService.utcNow()).thenReturn(now.minusSeconds(60));
        var token = jjwtTokenService.generateAccessToken(SUB);

        // when
        assertThrows(TokenException.class, () -> jjwtTokenService.getSubFromAccessToken(token));
    }
    
    @Test
    void whenGetSubFromRefreshToken_thenThrowException() {
        // given
        var now = ZonedDateTime.now(ZoneId.of("UTC"));
        when(dateTimeService.utcNow()).thenReturn(now.minusSeconds(60));
        var token = jjwtTokenService.generateRefreshToken(SUB);

        // when
        assertThrows(TokenException.class, () -> jjwtTokenService.getSubFromRefreshToken(token));
    }

    @Test
    void whenInvalidateTokensIsCalled_thenInvalidatedTokenRepositorySaveIsCalled() {
        var token = "token";
        var invalidatedToken = InvalidatedToken.builder()
            .token(token)
            .timeToLive((long) REFRESH_EXPIRATION)
            .build();
        
        jjwtTokenService.invalidateTokens(token);

        verify(invalidatedTokenRepository, times(1)).saveAll(List.of(invalidatedToken));
    }
    
}