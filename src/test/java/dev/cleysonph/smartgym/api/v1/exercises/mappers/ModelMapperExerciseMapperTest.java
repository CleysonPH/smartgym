package dev.cleysonph.smartgym.api.v1.exercises.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.cleysonph.smartgym.core.enums.MuscleGroup;
import dev.cleysonph.smartgym.core.models.Exercise;

@SpringBootTest
public class ModelMapperExerciseMapperTest {

    @Autowired
    private ModelMapperExerciseMapper modelMapperExerciseMapper;

    @Test
    void whenToExerciseResponseIsCalled_withValidExerciseResponse_shouldReturnExerciseResponse() {
        var exercise = Exercise.builder()
            .id(UUID.randomUUID())
            .name("Bench Press")
            .description("Bench Press Description")
            .instructions("Instructions 1;Instructions 2;Instructions 3")
            .imageUrl("https://example.com/image.jpg")
            .videoUrl("https://example.com/video.mp4")
            .muscleGroup(MuscleGroup.CHEST)
            .build();
        var instructions = exercise.getInstructions().split(";");
        
        var exerciseResponse = modelMapperExerciseMapper.toExerciseResponse(exercise);
        
        assertEquals(exercise.getId().toString(), exerciseResponse.getId());
        assertEquals(exercise.getName(), exerciseResponse.getName());
        assertEquals(exercise.getDescription(), exerciseResponse.getDescription());
        assertEquals(exercise.getMuscleGroup().toString(), exerciseResponse.getMuscleGroup());
        assertEquals(instructions.length, exerciseResponse.getInstructions().size());
        for (var instruction : instructions) {
            assertTrue(exerciseResponse.getInstructions().contains(instruction));
        }
        assertEquals(exercise.getImageUrl(), exerciseResponse.getImageUrl());
        assertEquals(exercise.getVideoUrl(), exerciseResponse.getVideoUrl());
    }
    
}