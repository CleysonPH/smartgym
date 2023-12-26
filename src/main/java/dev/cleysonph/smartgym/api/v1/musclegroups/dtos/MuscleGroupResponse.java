package dev.cleysonph.smartgym.api.v1.musclegroups.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MuscleGroupResponse {

    private String name;
    
}
