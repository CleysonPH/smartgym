package dev.cleysonph.smartgym.api.v1.musclegroups.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.cleysonph.smartgym.api.v1.musclegroups.dtos.MuscleGroupResponse;
import dev.cleysonph.smartgym.api.v1.musclegroups.services.MuscleGroupService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/muscle-groups")
public class MuscleGroupRestController {

    private final MuscleGroupService muscleGroupService;

    @GetMapping
    public List<MuscleGroupResponse> findAll() {
        return muscleGroupService.findAll();
    }
    
}
