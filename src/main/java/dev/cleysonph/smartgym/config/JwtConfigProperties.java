package dev.cleysonph.smartgym.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "dev.cleysonph.smartgym.jwt")
public class JwtConfigProperties {

    private String accessKey;
    private String refreshKey;
    private Integer accessExpiration;
    private Integer refreshExpiration;
    
}
