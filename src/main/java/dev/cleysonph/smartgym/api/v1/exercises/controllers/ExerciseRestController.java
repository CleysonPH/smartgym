package dev.cleysonph.smartgym.api.v1.exercises.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cleysonph.smartgym.api.v1.exercises.dtos.ExerciseResponse;
import dev.cleysonph.smartgym.api.v1.exercises.services.ExerciseService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exercises")
public class ExerciseRestController {

    private final ExerciseService exerciseService;

    @GetMapping
    public List<ExerciseResponse> findAll() {
        return exerciseService.findAll();
    }

    @GetMapping("/{id}")
    public ExerciseResponse findById(@PathVariable UUID id) {
        return exerciseService.findById(id);
    }
    
}
