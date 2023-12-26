package dev.cleysonph.smartgym.api.v1.exercises.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import dev.cleysonph.smartgym.api.v1.exercises.dtos.ExerciseResponse;
import dev.cleysonph.smartgym.core.models.Exercise;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ModelMapperExerciseMapper implements ExerciseMapper {

    private final ModelMapper modelMapper;

    @Override
    public ExerciseResponse toExerciseResponse(Exercise exercise) {
        return modelMapper.map(exercise, ExerciseResponse.class);
    }
    
}
