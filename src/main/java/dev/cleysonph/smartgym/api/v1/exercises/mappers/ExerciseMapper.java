package dev.cleysonph.smartgym.api.v1.exercises.mappers;

import dev.cleysonph.smartgym.api.v1.exercises.dtos.ExerciseResponse;
import dev.cleysonph.smartgym.core.models.Exercise;

public interface ExerciseMapper {

    ExerciseResponse toExerciseResponse(Exercise exercise);
    
}
