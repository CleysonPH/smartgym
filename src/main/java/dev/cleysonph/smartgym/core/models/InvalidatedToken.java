package dev.cleysonph.smartgym.core.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("invalidated_tokens")
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InvalidatedToken {

    @Id
    @ToString.Include
    @EqualsAndHashCode.Include
    private String token;

    @TimeToLive
    private Long timeToLive;
    
}
