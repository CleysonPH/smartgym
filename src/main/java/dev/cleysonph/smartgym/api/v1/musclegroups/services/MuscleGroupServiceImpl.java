package dev.cleysonph.smartgym.api.v1.musclegroups.services;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import dev.cleysonph.smartgym.api.v1.musclegroups.dtos.MuscleGroupResponse;
import dev.cleysonph.smartgym.core.enums.MuscleGroup;

@Service
public class MuscleGroupServiceImpl implements MuscleGroupService {

    @Override
    public List<MuscleGroupResponse> findAll() {
        return Stream.of(MuscleGroup.values())
            .map(muscleGroup -> new MuscleGroupResponse(muscleGroup.name()))
            .toList();
    }
    
}
