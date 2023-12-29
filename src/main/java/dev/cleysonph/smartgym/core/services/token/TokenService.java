package dev.cleysonph.smartgym.core.services.token;

import java.util.UUID;

public interface TokenService {

    String generateAccessToken(UUID sub);

    UUID getSubFromAccessToken(String token);

    String generateRefreshToken(UUID sub);

    UUID getSubFromRefreshToken(String token);

    void invalidateTokens(String ...tokens);
    
}
