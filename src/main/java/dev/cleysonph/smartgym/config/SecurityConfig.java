package dev.cleysonph.smartgym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.cleysonph.smartgym.api.v1.common.filters.AccessTokenRequestFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(
        HttpSecurity http, 
        AuthenticationEntryPoint authenticationEntryPoint,
        AccessTokenRequestFilter accessTokenRequestFilter
    ) throws Exception {
        return http
            .authorizeHttpRequests(customizer -> customizer
                .anyRequest().permitAll()
            )
            .sessionManagement(customizer -> customizer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .csrf(customizer -> customizer
                .ignoringRequestMatchers("/api/**")
            )
            .exceptionHandling(customizer -> customizer
                .authenticationEntryPoint(authenticationEntryPoint)
            )
            .addFilterBefore(accessTokenRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
}
