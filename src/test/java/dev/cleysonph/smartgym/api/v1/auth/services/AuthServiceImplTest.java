package dev.cleysonph.smartgym.api.v1.auth.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import dev.cleysonph.smartgym.api.v1.auth.dtos.LoginRequest;
import dev.cleysonph.smartgym.core.models.User;
import dev.cleysonph.smartgym.core.services.auth.AuthenticatedUser;
import dev.cleysonph.smartgym.core.services.token.TokenService;

class AuthServiceImplTest {

    private AuthServiceImpl authService;
    private TokenService tokenService;
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        tokenService = mock(TokenService.class);
        authenticationManager = mock(AuthenticationManager.class);
        authService = new AuthServiceImpl(tokenService, authenticationManager);
    }

    @Test
    void testLogin() {
        // Arrange
        var loginRequest = new LoginRequest("test@example.com", "password");
        var user = User.builder()
            .id(UUID.randomUUID())
            .email("test@mail.com")
            .password("password")
            .build();
        var authenticatedUser = new AuthenticatedUser(user);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword())))
                .thenReturn(new UsernamePasswordAuthenticationToken(authenticatedUser, null));

        when(tokenService.generateAccessToken(authenticatedUser.getUser().getId()))
                .thenReturn("access_token");
        when(tokenService.generateRefreshToken(authenticatedUser.getUser().getId()))
                .thenReturn("refresh_token");

        // Act
        var tokenResponse = authService.login(loginRequest);

        // Assert
        assertEquals("access_token", tokenResponse.getAccessToken());
        assertEquals("refresh_token", tokenResponse.getRefreshToken());
    }

    @Test
    void testLoginWithInvalidCredentials() {
        // Arrange
        var loginRequest = new LoginRequest("test@mail.com", "password");
        
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword())))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        try {
            authService.login(loginRequest);
        } catch (BadCredentialsException e) {
            assertEquals("Invalid credentials", e.getMessage());
        }
    }
}