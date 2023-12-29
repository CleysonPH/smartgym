package dev.cleysonph.smartgym.api.v1.common.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cleysonph.smartgym.api.v1.common.dtos.ErrorResponse;
import dev.cleysonph.smartgym.core.services.datetime.DateTimeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UnauthorizedHandlerTest {

    private UnauthorizedHandler unauthorizedHandler;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private AuthenticationException authException;
    private DateTimeService dateTimeService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        dateTimeService = mock(DateTimeService.class);
        unauthorizedHandler = new UnauthorizedHandler(objectMapper, dateTimeService);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        authException = mock(AuthenticationException.class);
    }

    @Test
    @SuppressWarnings("null")
    void testCommence() throws IOException, ServletException {
        // Arrange
        var now = ZonedDateTime.now(ZoneId.of("UTC"));
        ErrorResponse expectedResponse = ErrorResponse.builder()
                .message("Unauthorized")
                .timestamp(now)
                .build();
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        // Act
        when(dateTimeService.utcNow()).thenReturn(now);
        unauthorizedHandler.commence(request, response, authException);

        // Assert
        assertNotNull(response.getContentType());
        assertTrue(response.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE));
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals("UTF-8", response.getCharacterEncoding());
        assertEquals(expectedJson, response.getContentAsString());
    }
}