package dev.cleysonph.smartgym.core.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.cleysonph.smartgym.core.models.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
    
}
