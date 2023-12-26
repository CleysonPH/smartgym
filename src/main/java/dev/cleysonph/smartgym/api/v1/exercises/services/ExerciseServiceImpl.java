package dev.cleysonph.smartgym.api.v1.exercises.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.cleysonph.smartgym.api.v1.exercises.dtos.ExerciseResponse;
import dev.cleysonph.smartgym.api.v1.exercises.mappers.ExerciseMapper;
import dev.cleysonph.smartgym.core.repositories.ExerciseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseMapper exerciseMapper;
    private final ExerciseRepository exerciseRepository;

    @Override
    public List<ExerciseResponse> findAll() {
        return exerciseRepository.findAll()
            .stream()
            .map(exerciseMapper::toExerciseResponse)
            .toList();
    }
    
}
