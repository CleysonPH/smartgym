package dev.cleysonph.smartgym.api.v1.auth.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.cleysonph.smartgym.api.v1.auth.dtos.LoginRequest;
import dev.cleysonph.smartgym.api.v1.auth.dtos.RefreshRequest;
import dev.cleysonph.smartgym.api.v1.auth.dtos.TokenResponse;
import dev.cleysonph.smartgym.api.v1.auth.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody @Valid RefreshRequest refreshRequest) {
        return authService.refresh(refreshRequest);
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(code = HttpStatus.RESET_CONTENT)
    public void logout(@RequestBody @Valid RefreshRequest refreshRequest, @RequestHeader String authorization) {
        authService.logout(refreshRequest, authorization.replace("Bearer ", ""));
    }
    
}
