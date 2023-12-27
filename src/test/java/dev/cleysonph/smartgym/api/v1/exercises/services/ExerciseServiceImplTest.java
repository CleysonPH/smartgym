package dev.cleysonph.smartgym.api.v1.exercises.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.cleysonph.smartgym.api.v1.exercises.dtos.ExerciseResponse;
import dev.cleysonph.smartgym.api.v1.exercises.mappers.ExerciseMapper;
import dev.cleysonph.smartgym.core.enums.MuscleGroup;
import dev.cleysonph.smartgym.core.models.Exercise;
import dev.cleysonph.smartgym.core.repositories.ExerciseRepository;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceImplTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ExerciseMapper exerciseMapper;

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    @Test
    void whenFindAll_thenReturnListOfExerciseResponse() {
        var exercise = Exercise.builder()
            .id(UUID.randomUUID())
            .name("Exercise 1")
            .instructions("Instruction 1;Instruction 2")
            .muscleGroup(MuscleGroup.CHEST)
            .description("Description 1")
            .imageUrl("https://example.com/image.jpg")
            .videoUrl("https://example.com/video.mp4")
            .build();
        var exerciseResponse = ExerciseResponse.builder()
            .id(exercise.getId().toString())
            .name(exercise.getName())
            .instructions(List.of("Instruction 1", "Instruction 2"))
            .muscleGroup(exercise.getMuscleGroup().toString())
            .description(exercise.getDescription())
            .imageUrl(exercise.getImageUrl())
            .videoUrl(exercise.getVideoUrl())
            .build();

        when(exerciseRepository.findAll()).thenReturn(List.of(exercise));
        when(exerciseMapper.toExerciseResponse(exercise)).thenReturn(exerciseResponse);

        var exercises = exerciseService.findAll();
        
        verify(exerciseRepository, times(1)).findAll();
        verify(exerciseMapper, times(1)).toExerciseResponse(exercise);
        assertEquals(1, exercises.size());
        assertEquals(exerciseResponse, exercises.get(0));
    }

    @Test
    void whenFindAll_thenReturnEmptyListOfExerciseResponse() {
        when(exerciseRepository.findAll()).thenReturn(List.of());

        var exercises = exerciseService.findAll();
        
        verify(exerciseRepository, times(1)).findAll();
        verify(exerciseMapper, times(0)).toExerciseResponse(null);
        assertEquals(exercises.size(), 0);
    }
    
}
