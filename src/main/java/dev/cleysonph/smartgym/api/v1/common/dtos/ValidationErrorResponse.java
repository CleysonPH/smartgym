package dev.cleysonph.smartgym.api.v1.common.dtos;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

    private String message;

    private ZonedDateTime timestamp;
    
    private Map<String, List<String>> errors;
    
}
