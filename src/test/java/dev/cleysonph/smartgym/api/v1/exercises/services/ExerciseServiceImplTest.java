package dev.cleysonph.smartgym.api.v1.exercises.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
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
            .muscleGroups(Set.of(MuscleGroup.CHEST))
            .description("Description 1")
            .imageUrl("https://example.com/image.jpg")
            .videoUrl("https://example.com/video.mp4")
            .build();
        var exerciseResponse = ExerciseResponse.builder()
            .id(exercise.getId().toString())
            .name(exercise.getName())
            .instructions(List.of("Instruction 1", "Instruction 2"))
            .muscleGroups(exercise.getMuscleGroups())
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
        assertEquals(0, exercises.size());
    }

    @Test
    void whenFindById_thenReturnExerciseResponse() {
        var exercise = Exercise.builder()
            .id(UUID.randomUUID())
            .name("Exercise 1")
            .instructions("Instruction 1;Instruction 2")
            .muscleGroups(Set.of(MuscleGroup.CHEST))
            .description("Description 1")
            .imageUrl("https://example.com/image.jpg")
            .videoUrl("https://example.com/video.mp4")
            .build();
        var exerciseResponse = ExerciseResponse.builder()
            .id(exercise.getId().toString())
            .name(exercise.getName())
            .instructions(List.of("Instruction 1", "Instruction 2"))
            .muscleGroups(exercise.getMuscleGroups())
            .description(exercise.getDescription())
            .imageUrl(exercise.getImageUrl())
            .videoUrl(exercise.getVideoUrl())
            .build();

        when(exerciseRepository.findById(exercise.getId())).thenReturn(Optional.of(exercise));
        when(exerciseMapper.toExerciseResponse(exercise)).thenReturn(exerciseResponse);

        var exerciseFound = exerciseService.findById(exercise.getId());
        
        verify(exerciseRepository, times(1)).findById(exercise.getId());
        verify(exerciseMapper, times(1)).toExerciseResponse(exercise);
        assertEquals(exerciseResponse, exerciseFound);
    }

    @Test
    void whenFindById_thenThrowExerciseNotFoundException() {
        var exerciseId = UUID.randomUUID();

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        try {
            exerciseService.findById(exerciseId);
        } catch (Exception e) {
            assertEquals("Exercise not found", e.getMessage());
        }
        
        verify(exerciseRepository, times(1)).findById(exerciseId);
    }
    
}
