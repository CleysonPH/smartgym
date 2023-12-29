package dev.cleysonph.smartgym.api.v1.common.filters;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.cleysonph.smartgym.api.v1.common.dtos.ErrorResponse;
import dev.cleysonph.smartgym.core.exceptions.TokenException;
import dev.cleysonph.smartgym.core.models.User;
import dev.cleysonph.smartgym.core.repositories.UserRepository;
import dev.cleysonph.smartgym.core.services.auth.AuthenticatedUser;
import dev.cleysonph.smartgym.core.services.datetime.DateTimeService;
import dev.cleysonph.smartgym.core.services.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccessTokenRequestFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final DateTimeService dateTimeService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            tryDoFilterInternal(request);
        } catch (TokenException e) {
            handleTokenException(response, e);
        }
        filterChain.doFilter(request, response);
    }

    private void tryDoFilterInternal(HttpServletRequest request) {
        var header = request.getHeader("Authorization");
        if (isTokenPresent(header)) {
            var token = header.substring(7);
            var sub = tokenService.getSubFromAccessToken(token);
            var user = userRepository.findById(sub)
                .orElseThrow(() -> new TokenException("Invalid access token"));
            addUserToSpringSecurityContext(user, request);
        }
    }

    private void handleTokenException(HttpServletResponse response, TokenException e) throws IOException {
        var body = ErrorResponse.builder()
            .message(e.getLocalizedMessage())
            .timestamp(dateTimeService.utcNow())
            .build();
        var json = objectMapper.writeValueAsString(body);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    private boolean isTokenPresent(String header) {
        return header != null && header.startsWith("Bearer ");
    }

    private void addUserToSpringSecurityContext(User user, HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }
        var authenticatedUser = new AuthenticatedUser(user);
        var authentication = new UsernamePasswordAuthenticationToken(
            authenticatedUser, 
            null, 
            authenticatedUser.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
}
