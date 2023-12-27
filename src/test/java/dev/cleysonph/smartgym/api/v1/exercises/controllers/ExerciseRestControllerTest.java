package dev.cleysonph.smartgym.api.v1.exercises.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import dev.cleysonph.smartgym.api.v1.exercises.dtos.ExerciseResponse;
import dev.cleysonph.smartgym.api.v1.exercises.services.ExerciseService;
import dev.cleysonph.smartgym.core.exceptions.ExerciseNotFoundException;
import dev.cleysonph.smartgym.core.services.datetime.DateTimeService;

@WebMvcTest(ExerciseRestController.class)
class ExerciseRestControllerTest {

    @MockBean
    private ExerciseService exerciseService;

    @MockBean
    private DateTimeService dateTimeService;

    @Autowired
    private MockMvc mockMvc;

    private static final String EXERCISES_ENDPOINT = "/api/v1/exercises";
    private static final String EXERCISE_ENDPOINT = "/api/v1/exercises/{id}";

    @Test
    void whenGETExercises_thenReturnExerciseResponseList_200() throws Exception {
        var exerciseResponse = ExerciseResponse.builder()
            .id(UUID.randomUUID().toString())
            .name("Exercise 1")
            .instructions(List.of("Instruction 1", "Instruction 2"))
            .description("Description 1")
            .muscleGroup("CHEST")
            .imageUrl("https://example.com/image.jpg")
            .videoUrl("https://example.com/video.mp4")
            .build();
        var body = List.of(exerciseResponse);

        when(exerciseService.findAll()).thenReturn(body);

        mockMvc.perform(get(EXERCISES_ENDPOINT).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.length()").value(body.size()))
            .andExpect(jsonPath("$[0].id").value(exerciseResponse.getId()))
            .andExpect(jsonPath("$[0].name").value(exerciseResponse.getName()))
            .andExpect(jsonPath("$[0].instructions").isArray())
            .andExpect(jsonPath("$[0].instructions").isNotEmpty())
            .andExpect(jsonPath("$[0].instructions.length()").value(exerciseResponse.getInstructions().size()))
            .andExpect(jsonPath("$[0].instructions[0]").value(exerciseResponse.getInstructions().get(0)))
            .andExpect(jsonPath("$[0].instructions[1]").value(exerciseResponse.getInstructions().get(1)))
            .andExpect(jsonPath("$[0].description").value(exerciseResponse.getDescription()))
            .andExpect(jsonPath("$[0].muscle_group").value(exerciseResponse.getMuscleGroup()))
            .andExpect(jsonPath("$[0].image_url").value(exerciseResponse.getImageUrl()))
            .andExpect(jsonPath("$[0].video_url").value(exerciseResponse.getVideoUrl()));
    }

    @Test
    void whenGETExercises_thenReturnEmptyList_200() throws Exception {
        var body = List.<ExerciseResponse>of();

        when(exerciseService.findAll()).thenReturn(body);

        mockMvc.perform(get(EXERCISES_ENDPOINT).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void whenGETExercise_thenReturnExerciseResponse_200() throws Exception {
        var exerciseResponse = ExerciseResponse.builder()
            .id(UUID.randomUUID().toString())
            .name("Exercise 1")
            .instructions(List.of("Instruction 1", "Instruction 2"))
            .description("Description 1")
            .muscleGroup("CHEST")
            .imageUrl("https://example.com/image.jpg")
            .videoUrl("https://example.com/video.mp4")
            .build();

        when(exerciseService.findById(any(UUID.class))).thenReturn(exerciseResponse);

        mockMvc.perform(get(EXERCISE_ENDPOINT, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(exerciseResponse.getId()))
            .andExpect(jsonPath("$.name").value(exerciseResponse.getName()))
            .andExpect(jsonPath("$.instructions").isArray())
            .andExpect(jsonPath("$.instructions").isNotEmpty())
            .andExpect(jsonPath("$.instructions.length()").value(exerciseResponse.getInstructions().size()))
            .andExpect(jsonPath("$.instructions[0]").value(exerciseResponse.getInstructions().get(0)))
            .andExpect(jsonPath("$.instructions[1]").value(exerciseResponse.getInstructions().get(1)))
            .andExpect(jsonPath("$.description").value(exerciseResponse.getDescription()))
            .andExpect(jsonPath("$.muscle_group").value(exerciseResponse.getMuscleGroup()))
            .andExpect(jsonPath("$.image_url").value(exerciseResponse.getImageUrl()))
            .andExpect(jsonPath("$.video_url").value(exerciseResponse.getVideoUrl()));
    }

    @Test
    void whenGETExercise_thenReturn404() throws Exception {
        when(exerciseService.findById(any(UUID.class))).thenThrow(new ExerciseNotFoundException());

        mockMvc.perform(get(EXERCISE_ENDPOINT, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Exercise not found"));
    }

    
}

