package dev.cleysonph.smartgym.api.v1.musclegroups.services;

import java.util.List;

import dev.cleysonph.smartgym.api.v1.musclegroups.dtos.MuscleGroupResponse;

public interface MuscleGroupService {

    List<MuscleGroupResponse> findAll();
    
}
