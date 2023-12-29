package dev.cleysonph.smartgym.api.v1.common.handlers;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.cleysonph.smartgym.api.v1.common.dtos.ErrorResponse;
import dev.cleysonph.smartgym.core.services.datetime.DateTimeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UnauthorizedHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final DateTimeService dateTimeService;

    @Override
    public void commence(
        HttpServletRequest request, 
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException, ServletException {
        var body = ErrorResponse.builder()
            .message("Unauthorized")
            .timestamp(dateTimeService.utcNow())
            .build();
        var json = objectMapper.writeValueAsString(body);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
    
}
