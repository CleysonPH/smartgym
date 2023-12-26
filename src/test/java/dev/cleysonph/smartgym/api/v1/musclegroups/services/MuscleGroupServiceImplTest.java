package dev.cleysonph.smartgym.api.v1.musclegroups.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.cleysonph.smartgym.api.v1.musclegroups.dtos.MuscleGroupResponse;
import dev.cleysonph.smartgym.core.enums.MuscleGroup;

class MuscleGroupServiceImplTest {

    private MuscleGroupServiceImpl muscleGroupService;

    @BeforeEach
    void setUp() {
        muscleGroupService = new MuscleGroupServiceImpl();
    }

    @Test
    void whenFindAll_thenReturnListOfMuscleGroupResponse() {
        var expected = Stream.of(MuscleGroup.values())
            .map(muscleGroup -> new MuscleGroupResponse(muscleGroup.name()))
            .toList();
        var actual = muscleGroupService.findAll();
        assertEquals(expected.size(), actual.size());
        for (var muscleGroupResponse : actual) {
            assertTrue(expected.contains(muscleGroupResponse));
        }
    }
    
}
