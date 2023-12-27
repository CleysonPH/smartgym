package dev.cleysonph.smartgym.api.v1.exercises.dtos;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class ExerciseResponse {
    
    private String id;
    private String name;
    private String description;
    private String muscleGroup;
    private List<String> instructions;
    private String imageUrl;
    private String videoUrl;

}
