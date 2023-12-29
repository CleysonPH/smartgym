package dev.cleysonph.smartgym.api.v1.auth.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import dev.cleysonph.smartgym.api.v1.auth.dtos.LoginRequest;
import dev.cleysonph.smartgym.api.v1.auth.dtos.TokenResponse;
import dev.cleysonph.smartgym.api.v1.auth.services.AuthService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
class AuthRestControllerTest {

    private MockMvc mockMvc;
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        authService = mock(AuthService.class);
        AuthRestController authRestController = new AuthRestController(authService);
        mockMvc = MockMvcBuilders.standaloneSetup(authRestController).build();
    }

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

}