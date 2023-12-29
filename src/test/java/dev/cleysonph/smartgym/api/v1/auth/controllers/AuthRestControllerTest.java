package dev.cleysonph.smartgym.api.v1.auth.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.test.web.servlet.MockMvc;

import dev.cleysonph.smartgym.api.v1.auth.dtos.LoginRequest;
import dev.cleysonph.smartgym.api.v1.auth.dtos.TokenResponse;
import dev.cleysonph.smartgym.api.v1.auth.services.AuthService;
import dev.cleysonph.smartgym.config.SecurityConfig;
import dev.cleysonph.smartgym.core.services.datetime.DateTimeService;

@Import(SecurityConfig.class)
@WebMvcTest(AuthRestController.class)
class AuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AuthService authService;

    @MockBean
    private DateTimeService dateTimeService;

    @MockBean
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLogin() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");
        TokenResponse tokenResponse = new TokenResponse("access_token", "refresh_token");

        when(authService.login(loginRequest)).thenReturn(tokenResponse);

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void whenLoginWithInvalidEmail_thenReturnBadRequest() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("test", "password");

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.email[0]").value("must be a well-formed email address"));
    }

    @Test
    void whenLoginWithBlankEmail_thenReturnBadRequest() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("", "password");

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.email[0]").value("must not be blank"));
    }

    @Test
    void whenLoginWithBlankPassword_thenReturnBadRequest() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("test@mail.com", "");

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.password[0]").value("must not be blank"));
    }

}