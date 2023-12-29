package dev.cleysonph.smartgym.api.v1.auth.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import dev.cleysonph.smartgym.api.v1.auth.dtos.LoginRequest;
import dev.cleysonph.smartgym.api.v1.auth.dtos.RefreshRequest;
import dev.cleysonph.smartgym.api.v1.auth.dtos.TokenResponse;
import dev.cleysonph.smartgym.core.services.auth.AuthenticatedUser;
import dev.cleysonph.smartgym.core.services.token.TokenService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );
        var authenticateUser = (AuthenticatedUser) authentication.getPrincipal();
        var sub = authenticateUser.getUser().getId();
        var accessToken = tokenService.generateAccessToken(sub);
        var refreshToken = tokenService.generateRefreshToken(sub);
        return TokenResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    @Override
    public TokenResponse refresh(RefreshRequest refreshRequest) {
        var token = refreshRequest.getRefreshToken();
        var sub = tokenService.getSubFromRefreshToken(token);
        var accessToken = tokenService.generateAccessToken(sub);
        var refreshToken = tokenService.generateRefreshToken(sub);
        return TokenResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    @Override
    public void logout(RefreshRequest refreshRequest, String accessToken) {
        var token = refreshRequest.getRefreshToken();
        tokenService.invalidateTokens(token, accessToken);
    }
    
}
