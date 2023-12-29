package dev.cleysonph.smartgym.core.services.token;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.cleysonph.smartgym.config.JwtConfigProperties;
import dev.cleysonph.smartgym.core.exceptions.TokenException;
import dev.cleysonph.smartgym.core.services.datetime.DateTimeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JjwtTokenService implements TokenService {

    private final DateTimeService dateTimeService;
    private final JwtConfigProperties jwtConfigProperties;

    @Override
    public String generateAccessToken(UUID sub) {
        return generateToken(sub, jwtConfigProperties.getAccessKey(), jwtConfigProperties.getAccessExpiration());
    }

    @Override
    public UUID getSubFromAccessToken(String token) {
        var subject = getClaims(token, jwtConfigProperties.getAccessKey()).getSubject();
        return UUID.fromString(subject);
    }

    @Override
    public String generateRefreshToken(UUID sub) {
        return generateToken(sub, jwtConfigProperties.getRefreshKey(), jwtConfigProperties.getRefreshExpiration());
    }

    @Override
    public UUID getSubFromRefreshToken(String token) {
        var subject = getClaims(token, jwtConfigProperties.getRefreshKey()).getSubject();
        return UUID.fromString(subject);
    }

    private String generateToken(UUID sub, String key, Integer expiration) {
        var issuedAt = dateTimeService.utcNow();
        var expirationAt = issuedAt.plusSeconds(expiration);
        return Jwts.builder()
            .subject(sub.toString())
            .issuedAt(Date.from(issuedAt.toInstant()))
            .expiration(Date.from(expirationAt.toInstant()))
            .signWith(Keys.hmacShaKeyFor(key.getBytes()))
            .compact();
    }

    private Claims getClaims(String token, String key) {
        try {
            return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(key.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (JwtException e) {
            throw new TokenException(e.getLocalizedMessage());
        }
    }
    
}
