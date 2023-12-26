package dev.cleysonph.smartgym.api.v1.exercises.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
    
}
