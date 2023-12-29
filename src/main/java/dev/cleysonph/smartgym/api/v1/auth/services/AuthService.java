package dev.cleysonph.smartgym.api.v1.auth.services;

import dev.cleysonph.smartgym.api.v1.auth.dtos.LoginRequest;
import dev.cleysonph.smartgym.api.v1.auth.dtos.RefreshRequest;
import dev.cleysonph.smartgym.api.v1.auth.dtos.TokenResponse;

public interface AuthService {

    TokenResponse login(LoginRequest loginRequest);

    TokenResponse refresh(RefreshRequest refreshRequest);
    
}
