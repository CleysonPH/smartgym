package dev.cleysonph.smartgym.api.v1.exercises.services;

import java.util.List;

import dev.cleysonph.smartgym.api.v1.exercises.dtos.ExerciseResponse;

public interface ExerciseService {

    List<ExerciseResponse> findAll();
    
}
